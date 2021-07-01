package br.com.project.cleiton.geodata.middleware.main.domain;

import java.util.HashMap;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MainMessageCapsule {
    private String type;
    private String endpoint;
    private String path;
    private String lastOrigin;
    private String lestDestination;
    private String requestId;
    private String status;
    private String body;
    private HashMap<String, String> parameters;
}
