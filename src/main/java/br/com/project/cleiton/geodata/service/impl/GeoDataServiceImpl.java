package br.com.project.cleiton.geodata.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.project.cleiton.geodata.client.GoogleMapsApi;
import br.com.project.cleiton.geodata.domain.carlocation.CarLocation;
import br.com.project.cleiton.geodata.domain.carlocation.CarLocationResponseBody;
import br.com.project.cleiton.geodata.domain.googlemaps.geocode.GeoCode;
import br.com.project.cleiton.geodata.domain.gtc.carlocation.GtcCarLocation;
import br.com.project.cleiton.geodata.middleware.main.configuration.enums.MainApplication;
import br.com.project.cleiton.geodata.middleware.main.exception.MainErrorCallbackController;
import br.com.project.cleiton.geodata.middleware.main.domain.MainMessageCapsule;
import br.com.project.cleiton.geodata.middleware.main.service.api.MainServiceApi;
import br.com.project.cleiton.geodata.middleware.main.util.MainUtil;
import br.com.project.cleiton.geodata.service.api.GeoDataServiceApi;

@Service
public class GeoDataServiceImpl implements GeoDataServiceApi {

    @Autowired
    private GoogleMapsApi googleMapsApi;

    @Autowired
    private MainServiceApi cleitonService;

    @Autowired
    MainErrorCallbackController cleitonErrorCallback;

    @Override
    public String getDirections(HashMap<String, String> parameters) {

        String origin = parameters.get("origin");
        String destination = parameters.get("destination");
        String mode = parameters.get("mode");

        String directions = googleMapsApi.getDirections(origin, destination, mode);
        return directions;// MainUtil.jsonToObject(directions, Directions.class);

    }

    public List<String> getAddresses(String latitude, String longitude) {

        List<String> addresses = new ArrayList<String>();

        String geoCodeResponse = googleMapsApi.getGeocode(latitude, longitude);
        GeoCode geoCode = MainUtil.jsonToObject(geoCodeResponse, GeoCode.class);
        if (geoCode.status.equals("OK") && !geoCode.results.isEmpty()) {
            geoCode.results.forEach(address -> addresses.add(address.formattedAddress));
        }
        return addresses;
    }

    @Override
    public String getAddress(String latitude, String longitude) {
        List<String> addresses = new ArrayList<String>();
        addresses = getAddresses(latitude, longitude);
        return !addresses.isEmpty() ? addresses.get(0) : null;
    }

    @Override
    public void requestCarLocation(MainMessageCapsule cleitonMessageCapsule) {
        String destination = MainApplication.VEHICLE_DATA_HUB.label;
        cleitonService.sendRequest(cleitonMessageCapsule, destination);
    }

    @Override
    public void sendCarLocationCallBack(MainMessageCapsule cleitonMessageCapsule) {
        HashMap<String, String> parameters = cleitonMessageCapsule.getParameters();
        CarLocation carLocation = null;
        String status = parameters.get("status");
        String reason = parameters.get("reason");
        CarLocationResponseBody responseBody = CarLocationResponseBody.builder().status(status).reason(reason).build();

        if (status.equals("success")) {
            String body = cleitonMessageCapsule.getBody();
            GtcCarLocation gtcCarLocation = MainUtil.jsonToObject(body, GtcCarLocation.class);
            String latitude = gtcCarLocation.getGpsData().getCoordinate().getLatitude();
            String longitude = gtcCarLocation.getGpsData().getCoordinate().getLongitude();
            carLocation = CarLocation.builder().latitude(latitude).longitude(longitude).build();
            responseBody.setCarLocation(carLocation);
        }
        cleitonMessageCapsule.setBody(MainUtil.objectToJson(responseBody));
        cleitonService.sendCallback(cleitonMessageCapsule);
    }

    @Override
    public void sendErrorCallback(MainMessageCapsule cleitonMessageCapsule) {
        cleitonErrorCallback.pushError(cleitonMessageCapsule);
    }

}
