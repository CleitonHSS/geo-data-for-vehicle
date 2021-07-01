
package br.com.project.cleiton.geodata.domain.googlemaps.geocode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "lat", "lng" })
public class Southwest {

    @JsonProperty("lat")
    public Float lat;
    @JsonProperty("lng")
    public Float lng;

}
