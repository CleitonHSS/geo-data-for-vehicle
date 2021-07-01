
package br.com.project.cleiton.geodata.domain.gtc.carlocation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "datum", "latitude", "format", "longitude" })
@Data
public class Coordinate {

    @JsonProperty(required = false, value = "datum")
    public String datum;
    @JsonProperty(required = false, value = "latitude")
    public String latitude;
    @JsonProperty(required = false, value = "format")
    public String format;
    @JsonProperty(required = false, value = "longitude")
    public String longitude;

}
