
package br.com.project.cleiton.geodata.domain.googlemaps.geocode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlusCode {

    @JsonProperty("global_code")
    public String global_code = null;

}
