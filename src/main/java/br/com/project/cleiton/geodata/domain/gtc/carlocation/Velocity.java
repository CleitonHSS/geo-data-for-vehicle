
package br.com.project.cleiton.geodata.domain.gtc.carlocation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "unit", "value" })
@Data
public class Velocity {

    @JsonProperty(required = false, value = "unit")
    public String unit;
    @JsonProperty(required = false, value = "value")
    public String value;

}
