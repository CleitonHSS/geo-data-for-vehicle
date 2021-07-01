package br.com.project.cleiton.geodata.middleware.main.exception;

import br.com.project.cleiton.geodata.middleware.main.domain.exception.MainExceptionResponse;

public class MainMessageException extends RuntimeException {

    private static final long serialVersionUID = 8011193673466440726L;

    private final MainExceptionResponse response;

    public MainMessageException(MainExceptionResponse response) {
        this.response = response;
    }

    public MainExceptionResponse getResponse() {
        return response;
    }

}
