
package br.com.project.cleiton.geodata.domain.googlemaps.geocode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "bounds", "location", "location_type", "viewport" })
public class Geometry {

    @JsonProperty("bounds")
    public Bounds bounds;
    @JsonProperty("location")
    public Location location;
    @JsonProperty("location_type")
    public String locationType;
    @JsonProperty("viewport")
    public Viewport viewport;

}
