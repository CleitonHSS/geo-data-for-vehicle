package br.com.project.cleiton.geodata.domain.carlocation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "latitude", "longitude" })

@Data
@Builder
public class CarLocation {

    @JsonProperty(required = false, value = "latitude")
    public String latitude;
    @JsonProperty(required = false, value = "longitude")
    public String longitude;

}