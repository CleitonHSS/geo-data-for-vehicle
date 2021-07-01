package br.com.project.cleiton.geodata.middleware.main.exception;

import org.springframework.http.HttpStatus;

import br.com.project.cleiton.geodata.middleware.main.configuration.enums.exception.MainExceptionDescription;
import br.com.project.cleiton.geodata.middleware.main.configuration.enums.exception.MainExceptionType;

public class MainParameterException extends MainException {

    private static final long serialVersionUID = 8011193673466440726L;

    public MainParameterException(String message) {
        super(message);
        super.setStatus(HttpStatus.BAD_REQUEST);
        super.setType(MainExceptionType.PARAMETER_EXCEPTION.label);
        super.setDescription(MainExceptionDescription.PARAMETER_EXCEPTION.label);
    }

}
