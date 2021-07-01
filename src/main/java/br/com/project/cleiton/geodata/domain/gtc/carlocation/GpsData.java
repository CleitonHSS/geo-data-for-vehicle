
package br.com.project.cleiton.geodata.domain.gtc.carlocation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "dtTime", "coordinate", "courseHeading", "accuracy", "velocity" })
@Data
public class GpsData {

    @JsonProperty(required = false, value = "dtTime")
    public String dtTime;
    @JsonProperty(required = false, value = "coordinate")
    public Coordinate coordinate;
    @JsonProperty(required = false, value = "courseHeading")
    public Double courseHeading;
    @JsonProperty(required = false, value = "accuracy")
    public Accuracy accuracy;
    @JsonProperty(required = false, value = "velocity")
    public Velocity velocity;

}
