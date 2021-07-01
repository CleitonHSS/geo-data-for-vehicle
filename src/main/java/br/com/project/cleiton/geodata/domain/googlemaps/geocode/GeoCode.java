
package br.com.project.cleiton.geodata.domain.googlemaps.geocode;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        // "plus_code",
        "results", "status" })
public class GeoCode {

    // @JsonProperty("plus_code")
    // public PlusCode plusCode = null;
    @JsonProperty("results")
    public List<Result> results = null;
    @JsonProperty("status")
    public String status;

}
