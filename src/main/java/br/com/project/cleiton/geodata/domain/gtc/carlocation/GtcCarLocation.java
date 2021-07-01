
package br.com.project.cleiton.geodata.domain.gtc.carlocation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "gpsData", "ignition" })
@Data
public class GtcCarLocation {

    @JsonProperty(required = false, value = "gpsData")
    public GpsData gpsData;
    @JsonProperty(required = false, value = "ignition")
    public String ignition;
}
