package br.com.project.cleiton.geodata.domain.carlocation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor

public class CarLocationResponseBody {
    private String status;
    private String reason;
    private CarLocation carLocation;
}
