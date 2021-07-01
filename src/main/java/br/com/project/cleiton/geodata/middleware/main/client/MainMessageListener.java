package br.com.project.cleiton.geodata.middleware.main.client;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import br.com.project.cleiton.geodata.middleware.main.configuration.enums.MainCapsuleAttribute;
import br.com.project.cleiton.geodata.middleware.main.configuration.enums.MainMessageType;
import br.com.project.cleiton.geodata.middleware.main.configuration.enums.exception.MainExceptionDescription;
import br.com.project.cleiton.geodata.middleware.main.controller.MainControllerInterface;
import br.com.project.cleiton.geodata.middleware.main.exception.MainDataTransportException;
import br.com.project.cleiton.geodata.middleware.main.exception.MainErrorCallbackController;
import br.com.project.cleiton.geodata.middleware.main.domain.MainMessageCapsule;

@Component
public class MainMessageListener {

    @Autowired
    private MainControllerInterface cleitonController;

    @Autowired
    private MainErrorCallbackController cleitonErrorCallback;

    @Value("${spring.application.name}")
    private String thisApplication;

    @JmsListener(destination = "${message.listened-queue}")
    public void receiveMessage(HashMap<String, HashMap<String, String>> capsuleMap) {
        MainMessageCapsule cleitonMessageCapsule = messageCapsuleHashMapToObject(capsuleMap);
        try {
            cleitonController.controllerMapping(cleitonMessageCapsule);
        } catch (Exception e) {
            String exceptionMessage = e.getMessage() == null ? MainExceptionDescription.MESSAGE_PROCESSING_ERROR.label
                    : e.getMessage();
            MainDataTransportException exception = new MainDataTransportException(exceptionMessage);
            if (cleitonMessageCapsule.getType().equals(MainMessageType.NOTIFICATION.label))
                cleitonErrorCallback.sendException(cleitonMessageCapsule, exception);
            if (!cleitonMessageCapsule.getLastOrigin().equals(thisApplication))
                cleitonErrorCallback.sendException(cleitonMessageCapsule,
                        new MainDataTransportException(exceptionMessage));
        }
    }

    private MainMessageCapsule messageCapsuleHashMapToObject(HashMap<String, HashMap<String, String>> capsuleMap) {
        HashMap<String, String> capsuleAttributes = capsuleMap.get(MainCapsuleAttribute.ATTRIBUTES.label);
        HashMap<String, String> parameters = capsuleMap.get(MainCapsuleAttribute.PARAMETERS.label);

        return MainMessageCapsule.builder().type(capsuleAttributes.get(MainCapsuleAttribute.TYPE.label))
                .endpoint(capsuleAttributes.get(MainCapsuleAttribute.ENDPOINT.label))
                .path(capsuleAttributes.get(MainCapsuleAttribute.PATH.label))
                .requestId(capsuleAttributes.get(MainCapsuleAttribute.REQUESTID.label))
                .status(capsuleAttributes.get(MainCapsuleAttribute.STATUS.label))
                .lastOrigin(capsuleAttributes.get(MainCapsuleAttribute.ORIGIN.label))
                .lestDestination(capsuleAttributes.get(MainCapsuleAttribute.DESTINATION.label))
                .body(capsuleAttributes.get(MainCapsuleAttribute.BODY.label)).parameters(parameters).build();

    }
}
