package br.com.project.cleiton.geodata.middleware.main.configuration;

import org.springframework.context.annotation.Configuration;

import com.amazon.sqs.javamessaging.AmazonSQSMessagingClientWrapper;
import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.destination.DestinationResolver;

import br.com.project.cleiton.geodata.middleware.main.configuration.enums.MainLogString;
import lombok.extern.slf4j.Slf4j;

import javax.jms.JMSException;
import javax.jms.Session;

@Slf4j
@Configuration
public class SqsConfig {

    @Value("${message.listened-queue}")
    private String destinationQueue;

    @Value("${aws.region}")
    private String region = "sa-east-1";

    @Value("${spring.jmslistenercontainerfactory.concurrency}")
    private String concurrency;

    @Bean
    public DestinationResolver destinationResolver() {
        return new StaticDestinationResolver(destinationQueue);
    }

    SQSConnectionFactory connectionFactory = new SQSConnectionFactory(new ProviderConfiguration(),
            AmazonSQSClientBuilder.standard().withRegion(region)
                    .withCredentials(new DefaultAWSCredentialsProviderChain()));

    @Bean(name = "jmsListenerContainerFactory")
    public DefaultJmsListenerContainerFactory newDefaultJmsListenerContainerFactory() {
        destinationQueue = createQueueIfDoesNotExist();
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(this.connectionFactory);
        factory.setDestinationResolver(new StaticDestinationResolver(destinationQueue));
        factory.setConcurrency(concurrency);
        factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        return factory;
    }

    @Bean
    public JmsTemplate newJmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate(this.connectionFactory);
        jmsTemplate.setDestinationResolver(new StaticDestinationResolver(destinationQueue));
        return jmsTemplate;
    }

    private String createQueueIfDoesNotExist() {
        try {
            AmazonSQSMessagingClientWrapper clientWrapper = connectionFactory.createConnection()
                    .getWrappedAmazonSQSClient();
            if (!clientWrapper.queueExists(destinationQueue))
                clientWrapper.createQueue(destinationQueue);
        } catch (JMSException e) {
            log.error(MainLogString.ERROR.label);
            log.error(e.getMessage());

        }
        return destinationQueue;
    }

}