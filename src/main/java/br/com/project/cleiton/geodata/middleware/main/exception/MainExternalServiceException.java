package br.com.project.cleiton.geodata.middleware.main.exception;

import org.springframework.http.HttpStatus;

import br.com.project.cleiton.geodata.middleware.main.configuration.enums.exception.MainExceptionDescription;
import br.com.project.cleiton.geodata.middleware.main.configuration.enums.exception.MainExceptionType;

public class MainExternalServiceException extends MainException {

    private static final long serialVersionUID = 8011193673466440726L;

    public MainExternalServiceException(String message) {
        super(message);
        super.setStatus(HttpStatus.BAD_GATEWAY);
        super.setType(MainExceptionType.EXTERNAL_ERROR.label);
        super.setDescription(MainExceptionDescription.EXTERNAL_SERVICE_EXCEPTION.label);
    }

}
