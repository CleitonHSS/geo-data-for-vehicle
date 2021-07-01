package br.com.project.cleiton.geodata.middleware.main.service.api;

import br.com.project.cleiton.geodata.middleware.main.configuration.enums.MainLogString;
import br.com.project.cleiton.geodata.middleware.main.domain.MainMessageCapsule;

public interface MainServiceApi {

    Boolean sendRequest(MainMessageCapsule cleitonMessageCapsule, String destination);

    Boolean sendNotification(MainMessageCapsule cleitonMessageCapsule, String destination);

    Boolean sendCallback(MainMessageCapsule cleitonMessageCapsule);

    void logMainMessageFlow(MainMessageCapsule cleitonMessageCapsule, MainLogString cleitonLogString);

    void logMainSyncRequestInfo(MainLogString cleitonLogString, String event, String requestId, String userId);

    void logMainError(String errorType, String message);

    String setDestinationbyPath(MainMessageCapsule cleitonMessageCapsule);

}
