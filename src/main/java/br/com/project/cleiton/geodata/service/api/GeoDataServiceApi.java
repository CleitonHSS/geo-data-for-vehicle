package br.com.project.cleiton.geodata.service.api;

import java.util.List;
import java.util.HashMap;

import br.com.project.cleiton.geodata.middleware.main.domain.MainMessageCapsule;

public interface GeoDataServiceApi {
    String getDirections(HashMap<String, String> parameters);

    List<String> getAddresses(String latitude, String longitude);

    String getAddress(String latitude, String longitude);

    void requestCarLocation(MainMessageCapsule messageCapsule);

    void sendCarLocationCallBack(MainMessageCapsule cleitonMessageCapsule);

    void sendErrorCallback(MainMessageCapsule cleitonMessageCapsule);
}