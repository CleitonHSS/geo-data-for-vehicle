package br.com.project.cleiton.geodata.middleware.main.exception;

import org.springframework.http.HttpStatus;

import br.com.project.cleiton.geodata.middleware.main.configuration.enums.exception.MainExceptionDescription;
import br.com.project.cleiton.geodata.middleware.main.configuration.enums.exception.MainExceptionType;

public class MainDataTransportException extends MainException {

    private static final long serialVersionUID = 8011193673466440726L;

    public MainDataTransportException(String message) {
        super(message);
        super.setStatus(HttpStatus.CONFLICT);
        super.setType(MainExceptionType.TRANSPORT_EXCEPTION.label);
        super.setDescription(MainExceptionDescription.TRANSPORT_EXCEPTION.label);
    }

}
