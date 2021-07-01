package br.com.project.cleiton.geodata.middleware.main.domain.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GtcExceptionResponse {
    @JsonProperty(required = false, value = "type")
    private String type;
    @JsonProperty(required = false, value = "origin")
    private String origin;
    @JsonProperty(required = false, value = "gtcErrorCode")
    private String gtcErrorCode;
    @JsonProperty(required = false, value = "description")
    private String description;
    @JsonProperty(required = false, value = "message")
    private String message;
}
