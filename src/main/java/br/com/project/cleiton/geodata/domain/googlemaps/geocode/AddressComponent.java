
package br.com.project.cleiton.geodata.domain.googlemaps.geocode;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "long_name", "short_name", "types" })
public class AddressComponent {

    @JsonProperty("long_name")
    public String longName;
    @JsonProperty("short_name")
    public String shortName;
    @JsonProperty("types")
    public List<String> types = null;

}
