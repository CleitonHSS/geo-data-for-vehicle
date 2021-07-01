package br.com.project.cleiton.geodata.controller.web;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.project.cleiton.geodata.middleware.main.configuration.enums.MainApplication;
import br.com.project.cleiton.geodata.middleware.main.configuration.enums.MainLogString;
import br.com.project.cleiton.geodata.middleware.main.configuration.enums.MainMessageType;
import br.com.project.cleiton.geodata.middleware.main.domain.MainMessageCapsule;
import br.com.project.cleiton.geodata.middleware.main.domain.MainStatusResponse;
import br.com.project.cleiton.geodata.middleware.main.service.impl.MainService;
import br.com.project.cleiton.geodata.service.api.GeoHeartBeatServiceApi;
import br.com.project.cleiton.geodata.service.api.GeoDataServiceApi;

@RestController
public class GeoDataController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeoDataController.class);

    @Autowired
    private GeoDataServiceApi geodataServiceApi;

    @Autowired
    private GeoHeartBeatServiceApi geoHeartBeatServiceApi;

    @Autowired
    private MainService cleitonService;

    @GetMapping(value = "/heartbeat")
    public @ResponseBody MainStatusResponse heartbeat() {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("heartbeat() - enter");
        }
        return new MainStatusResponse(true, geoHeartBeatServiceApi.heartbeat());
    }

    @GetMapping(value = "/directions")
    public ResponseEntity<String> directions(@RequestHeader(required = true) String origin,
            @RequestHeader(required = true) String destination, @RequestHeader(required = true) String mode) {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("origin", origin);
        parameters.put("destination", destination);
        parameters.put("mode", mode);

        String directionsResponseBody = geodataServiceApi.getDirections(parameters);

        return new ResponseEntity<String>(directionsResponseBody, HttpStatus.OK);
    }

    @GetMapping(value = "/address")
    public ResponseEntity<String> address(@RequestHeader(required = true) String longitude,
            @RequestHeader(required = true) String latitude) {
        cleitonService.logMainSyncRequestInfo(MainLogString.REQUEST_RECEIVED, "/address", latitude + "," + longitude,
                null);
        String addresses = geodataServiceApi.getAddress(latitude, longitude);
        cleitonService.logMainSyncRequestInfo(MainLogString.RESPONSE_SENT, "/address", latitude + "," + longitude,
                null);

        return new ResponseEntity<String>(addresses, HttpStatus.OK);
    }

}
