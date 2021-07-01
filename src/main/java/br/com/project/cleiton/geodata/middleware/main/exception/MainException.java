package br.com.project.cleiton.geodata.middleware.main.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainException extends RuntimeException {

    private static final long serialVersionUID = 8011193673466440726L;

    private HttpStatus status;
    private String type;
    private String description;

    public MainException(String message) {
        super(message);
    }
}
