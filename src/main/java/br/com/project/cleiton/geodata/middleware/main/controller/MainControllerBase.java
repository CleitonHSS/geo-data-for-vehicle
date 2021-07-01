package br.com.project.cleiton.geodata.middleware.main.controller;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.project.cleiton.geodata.middleware.main.configuration.annotation.MainMapping;
import br.com.project.cleiton.geodata.middleware.main.configuration.enums.MainLogString;
import br.com.project.cleiton.geodata.middleware.main.configuration.enums.MainMessageType;
import br.com.project.cleiton.geodata.middleware.main.configuration.enums.exception.MainExceptionMessage;
import br.com.project.cleiton.geodata.middleware.main.exception.MainDataTransportException;
import br.com.project.cleiton.geodata.middleware.main.exception.MainErrorCallbackController;
import br.com.project.cleiton.geodata.middleware.main.exception.MainParameterException;
import br.com.project.cleiton.geodata.middleware.main.domain.MainMessageCapsule;
import br.com.project.cleiton.geodata.middleware.main.service.api.MainServiceApi;

@Controller
public class MainControllerBase {

    @Autowired
    private MainServiceApi cleitonService;

    @Autowired
    private MainErrorCallbackController cleitonErrorCallbackController;

    public void controllerMapping(MainMessageCapsule cleitonMessageCapsule) {

        String type = cleitonMessageCapsule.getType();
        String endpoint = cleitonMessageCapsule.getEndpoint();
        String message = String.format(MainExceptionMessage.INVALID_TYPE_OR_ENDPOINT.label,
                cleitonMessageCapsule.getType(), cleitonMessageCapsule.getEndpoint());
        MainParameterException error = new MainParameterException(message);
        Boolean validMethod = false;
        Class<?> obj = this.getClass();

        for (Method method : obj.getDeclaredMethods()) {
            if (method.isAnnotationPresent(MainMapping.class)) {
                Annotation annotation = method.getAnnotation(MainMapping.class);
                MainMapping cleitonMapping = (MainMapping) annotation;
                if (cleitonMapping.type().equals(type) && cleitonMapping.endpoint().equals(endpoint)) {
                    validMethod = true;
                    try {
                        method.invoke(this, cleitonMessageCapsule);
                    } catch (Exception ex) {
                        throw new MainDataTransportException(ex.getMessage());
                    }
                }
            }
        }
        if (!validMethod && !cleitonMessageCapsule.getType().equals(MainMessageType.NOTIFICATION.label)) {
            throw new MainDataTransportException(message);
        }
        if (!validMethod && !cleitonMessageCapsule.getType().equals(MainMessageType.ERROR.label)) {
            cleitonErrorCallbackController.sendException(cleitonMessageCapsule, error);

        } else if (!validMethod && cleitonMessageCapsule.getType().equals(MainMessageType.ERROR.label)) {
            cleitonService.logMainMessageFlow(cleitonMessageCapsule, MainLogString.ERROR_RECEIVED);
            cleitonErrorCallbackController.pushError(cleitonMessageCapsule);
        }
    }

}
