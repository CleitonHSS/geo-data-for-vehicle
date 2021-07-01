
package br.com.project.cleiton.geodata.domain.googlemaps.geocode;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "address_components", "formatted_address", "geometry",
        // "plus_code",
        "place_id", "types" })
public class Result {

    @JsonProperty("address_components")
    public List<AddressComponent> addressComponents = null;
    @JsonProperty("formatted_address")
    public String formattedAddress;
    @JsonProperty("geometry")
    public Geometry geometry;
    // @JsonProperty("plus_code")
    // public PlusCode plusCode;
    @JsonProperty("place_id")
    public String placeId;
    @JsonProperty("types")
    public List<String> types = null;

}
