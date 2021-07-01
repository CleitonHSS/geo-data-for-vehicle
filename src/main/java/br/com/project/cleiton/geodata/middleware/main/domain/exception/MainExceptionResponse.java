package br.com.project.cleiton.geodata.middleware.main.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MainExceptionResponse {
    private String type;
    private String origin;
    private String description;
    private String message;
}
