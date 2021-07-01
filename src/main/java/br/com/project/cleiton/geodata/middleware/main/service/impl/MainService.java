package br.com.project.cleiton.geodata.middleware.main.service.impl;

import br.com.project.cleiton.geodata.middleware.main.client.MainMessageSender;
import br.com.project.cleiton.geodata.middleware.main.configuration.enums.MainLogString;
import br.com.project.cleiton.geodata.middleware.main.configuration.enums.MainMessageType;
import br.com.project.cleiton.geodata.middleware.main.domain.MainMessageCapsule;
import br.com.project.cleiton.geodata.middleware.main.service.api.MainServiceApi;
import br.com.project.cleiton.geodata.middleware.main.util.MainUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MainService implements MainServiceApi {

    @Autowired
    private MainMessageSender cleitonMessageSender;

    @Value("${spring.application.name}")
    private String thisApplication;

    public Boolean sendRequest(MainMessageCapsule cleitonMessageCapsule, String destination) {
        Boolean sent = false;
        cleitonMessageCapsule.setLestDestination(destination);
        cleitonMessageCapsule.setLastOrigin(thisApplication);
        cleitonMessageCapsule.setType(MainMessageType.REQUEST.label);
        if (cleitonMessageSender.send(cleitonMessageCapsule)) {
            sent = true;
            logMainMessageFlow(cleitonMessageCapsule, MainLogString.REQUEST_SENT);
        }
        return sent;
    }

    public Boolean sendNotification(MainMessageCapsule cleitonMessageCapsule, String destination) {
        Boolean sent = false;
        cleitonMessageCapsule.setLestDestination(destination);
        cleitonMessageCapsule.setLastOrigin(thisApplication);
        cleitonMessageCapsule.setType(MainMessageType.NOTIFICATION.label);
        if (cleitonMessageSender.send(cleitonMessageCapsule)) {
            sent = true;
            logMainMessageFlow(cleitonMessageCapsule, MainLogString.NOTIFICATION_SENT);
        }
        return sent;
    }

    @Override
    public Boolean sendCallback(MainMessageCapsule cleitonMessageCapsule) {
        Boolean sent = false;
        String status = Integer.toString(HttpStatus.OK.value());
        cleitonMessageCapsule.setStatus(status);
        cleitonMessageCapsule.setType(MainMessageType.CALLBACK.label);

        String destination = setDestinationbyPath(cleitonMessageCapsule);
        cleitonMessageCapsule.setLestDestination(destination);
        cleitonMessageCapsule.setLastOrigin(thisApplication);

        if (cleitonMessageSender.send(cleitonMessageCapsule)) {
            sent = true;
            logMainMessageFlow(cleitonMessageCapsule, MainLogString.CALLBACK_SENT);
        }
        return sent;
    }

    @Override
    public String setDestinationbyPath(MainMessageCapsule messageCapsule) {
        String nextDestination = "";
        if ((messageCapsule.getType().equals(MainMessageType.CALLBACK.label)
                || messageCapsule.getType().equals(MainMessageType.ERROR.label))
                && Objects.nonNull(messageCapsule.getPath())) {
            String[] path = messageCapsule.getPath().split("//", 0);
            nextDestination = path[path.length - 1];
        }
        return nextDestination;
    }

    public void logMainMessageFlow(MainMessageCapsule cleitonMessageCapsule, MainLogString cleitonLogString) {
        if (cleitonLogString.equals(MainLogString.ERROR) || cleitonLogString.equals(MainLogString.ERROR_RECEIVED)
                || cleitonLogString.equals(MainLogString.ERROR_CALLBACK)
                || cleitonLogString.equals(MainLogString.ERROR_CALLBACK_SENT)) {
            log.error(cleitonLogString.label);
            log.error("Event Time: " + MainUtil.getXEventTime());
            log.error("Endpoint: " + cleitonMessageCapsule.getEndpoint());
            log.error("RequestUuId: " + cleitonMessageCapsule.getRequestId());
            log.error("Status: " + cleitonMessageCapsule.getStatus());
            log.error("Body: " + cleitonMessageCapsule.getBody());
            log.error("Origin: " + cleitonMessageCapsule.getLastOrigin());
            log.error("Destination: " + cleitonMessageCapsule.getLestDestination());
        } else {
            log.info(cleitonLogString.label);
            log.info("Event Time: " + MainUtil.getXEventTime());
            log.info("Endpoint: " + cleitonMessageCapsule.getEndpoint());
            log.info("RequestUuId: " + cleitonMessageCapsule.getRequestId());
            log.info("Origin: " + cleitonMessageCapsule.getLastOrigin());
            log.info("Destination: " + cleitonMessageCapsule.getLestDestination());
        }
    }

    public void logMainSyncRequestInfo(MainLogString cleitonLogString, String event, String requestId, String userId) {
        log.info(cleitonLogString.label);
        log.info("Event Time: " + MainUtil.getXEventTime());
        log.info("Event: " + event);
        log.info("Id: " + requestId);
        log.info("User Id: " + userId);
    }

    public void logMainError(String errorType, String message) {
        log.error(MainLogString.ERROR.label);
        log.error("Time: " + MainUtil.getXEventTime());
        log.error("Type: " + errorType);
        log.error("Message: " + message);
    }

}