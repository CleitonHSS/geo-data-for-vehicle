package br.com.project.cleiton.geodata.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import br.com.project.cleiton.geodata.service.api.GeoHeartBeatServiceApi;

@Service
public class GeoHeartBeatServiceImpl implements GeoHeartBeatServiceApi {

    @Override
    public String heartbeat() {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }

}
