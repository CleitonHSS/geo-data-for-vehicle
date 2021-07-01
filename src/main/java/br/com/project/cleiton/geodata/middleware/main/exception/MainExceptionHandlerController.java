package br.com.project.cleiton.geodata.middleware.main.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.project.cleiton.geodata.middleware.main.configuration.enums.exception.MainExceptionDescription;
import br.com.project.cleiton.geodata.middleware.main.configuration.enums.exception.MainExceptionType;
import br.com.project.cleiton.geodata.middleware.main.domain.exception.MainExceptionResponse;

@ControllerAdvice
public class MainExceptionHandlerController extends ResponseEntityExceptionHandler {

    @Value("${spring.application.name}")
    private String thisApplication;

    @ExceptionHandler(MainExternalServiceException.class)
    @ResponseStatus(value = HttpStatus.BAD_GATEWAY)
    @ResponseBody
    public MainExceptionResponse handleExternalException(MainExternalServiceException ex) {
        MainExceptionResponse response = new MainExceptionResponse(ex.getType(), thisApplication, ex.getDescription(),
                ex.getMessage());
        return response;
    }

    @ExceptionHandler(MainParameterException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MainExceptionResponse handleParameterException(MainParameterException ex) {
        MainExceptionResponse response = new MainExceptionResponse(ex.getType(), thisApplication, ex.getDescription(),
                ex.getMessage());
        return response;
    }

    @ExceptionHandler(MainDataTransportException.class)
    @ResponseStatus(value = HttpStatus.BAD_GATEWAY)
    @ResponseBody
    public MainExceptionResponse handleTransportException(MainDataTransportException ex) {
        MainExceptionResponse response = new MainExceptionResponse(ex.getType(), thisApplication, ex.getDescription(),
                ex.getMessage());
        return response;

    }

    @ExceptionHandler(MainInternalServerException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public MainExceptionResponse handleInternalServerException(MainInternalServerException ex) {
        MainExceptionResponse response = new MainExceptionResponse(ex.getType(), thisApplication, ex.getDescription(),
                ex.getMessage());
        return response;

    }

    @ExceptionHandler(MainMessageException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MainExceptionResponse handleBrokerMessageException(MainMessageException ex) {
        return ex.getResponse();
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        MainExceptionResponse errorResponse = new MainExceptionResponse(MainExceptionType.PARAMETER_EXCEPTION.label,
                thisApplication, MainExceptionDescription.PARAMETER_EXCEPTION.label, ex.getMessage());
        return ResponseEntity.status(status).body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        MainExceptionResponse errorResponse = new MainExceptionResponse(MainExceptionType.PARAMETER_EXCEPTION.label,
                thisApplication, MainExceptionDescription.PARAMETER_EXCEPTION.label, ex.getMessage());
        return ResponseEntity.status(status).body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        MainExceptionResponse errorResponse = new MainExceptionResponse(MainExceptionType.PATH_EXCEPTION.label,
                thisApplication, MainExceptionDescription.PARAMETER_EXCEPTION.label, ex.getMessage());
        return ResponseEntity.status(status).body(errorResponse);
    }

}
