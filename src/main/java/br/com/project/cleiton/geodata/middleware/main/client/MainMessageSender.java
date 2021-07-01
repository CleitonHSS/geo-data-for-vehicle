package br.com.project.cleiton.geodata.middleware.main.client;

import br.com.project.cleiton.geodata.middleware.main.domain.MainMessageCapsule;
import br.com.project.cleiton.geodata.middleware.main.configuration.enums.MainApplication;
import br.com.project.cleiton.geodata.middleware.main.configuration.enums.MainCapsuleAttribute;
import br.com.project.cleiton.geodata.middleware.main.configuration.enums.MainLogString;
import br.com.project.cleiton.geodata.middleware.main.configuration.enums.MainMessageType;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

import com.amazon.sqs.javamessaging.AmazonSQSMessagingClientWrapper;
import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

@Slf4j
@Component
public class MainMessageSender {

    @Value("${message.destination-queue.detination}")
    private String detinationQueue;

    @Value("${spring.application.name}")
    private String thisApplication;
    @Value("${aws.region}")
    private String region;

    public Boolean send(MainMessageCapsule cleitonMessageCapsule) {
        Boolean success = false;
        String destinationQueue = setDestinationQueue(cleitonMessageCapsule.getLestDestination());
        cleitonMessageCapsule = pathFactory(cleitonMessageCapsule);
        try {
            HashMap<String, HashMap<String, String>> capsule = messageCapsuleObjectToHashMap(cleitonMessageCapsule);
            SQSConnection connection = createConnection(destinationQueue);
            try {
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                Queue queue = session.createQueue(createQueueIfDoesNotExist(connection, destinationQueue));
                MessageProducer producer = session.createProducer(queue);
                ObjectMessage message = session.createObjectMessage(capsule);
                producer.send(message);
                success = true;
            } finally {
                connection.close();
            }
        } catch (Exception e) {
            log.error(MainLogString.ERROR.label);
            log.error(e.getMessage());

        }
        return success;
    }

    private SQSConnection createConnection(String destinationQueue) throws JMSException {
        SQSConnectionFactory connectionFactory = new SQSConnectionFactory(new ProviderConfiguration(),
                AmazonSQSClientBuilder.standard().withRegion(region)
                        .withCredentials(new DefaultAWSCredentialsProviderChain()));
        SQSConnection connection = connectionFactory.createConnection();

        AmazonSQSMessagingClientWrapper clientWrapper = connection.getWrappedAmazonSQSClient();
        if (!clientWrapper.queueExists(destinationQueue))
            clientWrapper.createQueue(destinationQueue);
        return connection;
    }

    private String createQueueIfDoesNotExist(SQSConnection connection, String destinationQueue) throws JMSException {
        AmazonSQSMessagingClientWrapper clientWrapper = connection.getWrappedAmazonSQSClient();
        if (!clientWrapper.queueExists(destinationQueue))
            clientWrapper.createQueue(destinationQueue);
        return destinationQueue;
    }

    // @formatter:off
    private String setDestinationQueue(String destination) {
        String queueDestination = destination.equals(MainApplication.BFF_ANDROID.label) ? detinationQueue : "Unknown";
        return !destination.equals(thisApplication) ? queueDestination : "Unknown";
    };
    // @formatter:on

    private MainMessageCapsule pathFactory(MainMessageCapsule cleitonMessageCapsule) {
        String destination = cleitonMessageCapsule.getLestDestination();
        if (cleitonMessageCapsule.getType().equals(MainMessageType.CALLBACK.label)
                || cleitonMessageCapsule.getType().equals(MainMessageType.ERROR.label))
            cleitonMessageCapsule.setPath(cleitonMessageCapsule.getPath().replace("//" + destination, ""));
        else
            cleitonMessageCapsule.setPath(cleitonMessageCapsule.getPath() + "//" + thisApplication);
        cleitonMessageCapsule.setPath(cleitonMessageCapsule.getPath().replace("null", ""));
        return cleitonMessageCapsule;
    }

    private HashMap<String, HashMap<String, String>> messageCapsuleObjectToHashMap(
            MainMessageCapsule cleitonMessageCapsule) {
        HashMap<String, String> capsuleAttributes = new HashMap<>();
        capsuleAttributes.put(MainCapsuleAttribute.TYPE.label, cleitonMessageCapsule.getType());
        capsuleAttributes.put(MainCapsuleAttribute.ENDPOINT.label, cleitonMessageCapsule.getEndpoint());
        capsuleAttributes.put(MainCapsuleAttribute.PATH.label, cleitonMessageCapsule.getPath());
        capsuleAttributes.put(MainCapsuleAttribute.REQUESTID.label, cleitonMessageCapsule.getRequestId());
        capsuleAttributes.put(MainCapsuleAttribute.STATUS.label, cleitonMessageCapsule.getStatus());
        capsuleAttributes.put(MainCapsuleAttribute.ORIGIN.label, cleitonMessageCapsule.getLastOrigin());
        capsuleAttributes.put(MainCapsuleAttribute.DESTINATION.label, cleitonMessageCapsule.getLestDestination());
        capsuleAttributes.put(MainCapsuleAttribute.BODY.label, cleitonMessageCapsule.getBody());
        HashMap<String, HashMap<String, String>> capsule = new HashMap<>();
        capsule.put(MainCapsuleAttribute.ATTRIBUTES.label, capsuleAttributes);
        capsule.put(MainCapsuleAttribute.PARAMETERS.label, cleitonMessageCapsule.getParameters());
        return capsule;
    }

}
