
package br.com.project.cleiton.geodata.domain.googlemaps.geocode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "northeast", "southwest" })
public class Viewport {

    @JsonProperty("northeast")
    public Northeast_ northeast;
    @JsonProperty("southwest")
    public Southwest_ southwest;

}
