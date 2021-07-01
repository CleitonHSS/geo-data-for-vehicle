package br.com.project.cleiton.geodata.controller.cleiton;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.project.cleiton.geodata.middleware.main.domain.MainMessageCapsule;
import br.com.project.cleiton.geodata.middleware.main.controller.MainControllerBase;
import br.com.project.cleiton.geodata.middleware.main.controller.MainControllerInterface;
import br.com.project.cleiton.geodata.service.api.GeoDataServiceApi;
import br.com.project.cleiton.geodata.middleware.main.service.api.MainServiceApi;
import br.com.project.cleiton.geodata.middleware.main.configuration.annotation.MainMapping;
import br.com.project.cleiton.geodata.middleware.main.configuration.enums.MainLogString;

@Controller
public class MainController extends MainControllerBase implements MainControllerInterface {

    @Autowired
    GeoDataServiceApi geodataServiceApi;

    @Autowired
    MainServiceApi cleitonService;

    // car-location
    @MainMapping(type = "REQUEST", endpoint = "car-location")
    public void carlocationRequest(MainMessageCapsule cleitonMessageCapsule) {
        cleitonService.logMainMessageFlow(cleitonMessageCapsule, MainLogString.REQUEST_RECEIVED);
        geodataServiceApi.requestCarLocation(cleitonMessageCapsule);
    }

    @MainMapping(type = "CALLBACK", endpoint = "car-location")
    public void carlocationCallback(MainMessageCapsule cleitonMessageCapsule) {
        cleitonService.logMainMessageFlow(cleitonMessageCapsule, MainLogString.CALLBACK_RECEIVED);
        geodataServiceApi.sendCarLocationCallBack(cleitonMessageCapsule);
    }

    @MainMapping(type = "ERROR", endpoint = "car-location")
    public void carlocationErrorCallback(MainMessageCapsule cleitonMessageCapsule) {
        cleitonService.logMainMessageFlow(cleitonMessageCapsule, MainLogString.ERROR_RECEIVED);
        geodataServiceApi.sendErrorCallback(cleitonMessageCapsule);
    }

}
