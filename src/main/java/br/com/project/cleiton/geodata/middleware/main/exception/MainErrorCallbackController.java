package br.com.project.cleiton.geodata.middleware.main.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.project.cleiton.geodata.middleware.main.client.MainMessageSender;
import br.com.project.cleiton.geodata.middleware.main.configuration.enums.MainLogString;
import br.com.project.cleiton.geodata.middleware.main.configuration.enums.MainMessageType;
import br.com.project.cleiton.geodata.middleware.main.configuration.enums.exception.MainExceptionDescription;
import br.com.project.cleiton.geodata.middleware.main.configuration.enums.exception.MainExceptionMessage;
import br.com.project.cleiton.geodata.middleware.main.configuration.enums.exception.MainExceptionType;
import br.com.project.cleiton.geodata.middleware.main.domain.MainMessageCapsule;
import br.com.project.cleiton.geodata.middleware.main.domain.exception.MainExceptionResponse;
import br.com.project.cleiton.geodata.middleware.main.service.impl.MainService;
import br.com.project.cleiton.geodata.middleware.main.util.MainUtil;

@Component
public class MainErrorCallbackController {

    @Autowired
    private MainService cleitonService;

    @Autowired
    private MainMessageSender cleitonMessageSender;

    @Value("${spring.application.name}")
    private String thisApplication;

    public Boolean sendException(MainMessageCapsule cleitonMessageCapsule, MainException cleitonException) {
        Boolean sent = false;
        String status = Integer.toString(HttpStatus.INTERNAL_SERVER_ERROR.value());
        MainExceptionResponse cleitonExceptionResponse = MainExceptionResponse.builder()
                .type(MainExceptionType.INTERNAL_SERVER_ERROR.label).origin(thisApplication)
                .description(MainExceptionDescription.INTERNAL_SERVER_ERROR.label)
                .message(MainExceptionMessage.MESSAGE_UNKNOWN_ERROR.label).build();

        if (cleitonException != null) {
            status = Integer.toString(cleitonException.getStatus().value());
            cleitonExceptionResponse = MainExceptionResponse.builder().type(cleitonException.getType())
                    .description(cleitonException.getDescription()).message(cleitonException.getMessage()).build();
        }

        String body = MainUtil.objectToJson(cleitonExceptionResponse);
        cleitonMessageCapsule.setType(MainMessageType.ERROR.label);
        cleitonMessageCapsule.setStatus(status);
        cleitonMessageCapsule.setBody(body);

        String destination = cleitonService.setDestinationbyPath(cleitonMessageCapsule);
        cleitonMessageCapsule.setLestDestination(destination);
        cleitonMessageCapsule.setLastOrigin(thisApplication);

        if (cleitonMessageSender.send(cleitonMessageCapsule)) {
            sent = true;
            cleitonService.logMainMessageFlow(cleitonMessageCapsule, MainLogString.ERROR_CALLBACK_SENT);
        }

        return sent;
    }

    public Boolean pushError(MainMessageCapsule cleitonMessageCapsule) {
        Boolean sent = false;
        String destination = cleitonService.setDestinationbyPath(cleitonMessageCapsule);
        cleitonMessageCapsule.setLestDestination(destination);
        cleitonMessageCapsule.setLastOrigin(thisApplication);
        if (cleitonMessageSender.send(cleitonMessageCapsule)) {
            sent = true;
            cleitonService.logMainMessageFlow(cleitonMessageCapsule, MainLogString.ERROR_CALLBACK_SENT);
        }
        return sent;
    }

}
