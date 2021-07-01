package br.com.project.cleiton.geodata.middleware.main.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MainStatusResponse {

    private Boolean success;
    private String message;

}
