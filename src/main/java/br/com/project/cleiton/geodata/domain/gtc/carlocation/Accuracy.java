
package br.com.project.cleiton.geodata.domain.gtc.carlocation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "pdop", "hdop" })
@Data
public class Accuracy {

    @JsonProperty(required = false, value = "pdop")
    public String pdop;
    @JsonProperty(required = false, value = "hdop")
    public String hdop;

}
