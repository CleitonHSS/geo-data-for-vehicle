package br.com.project.cleiton.geodata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class GeoDataApplication {
    public static void main(String[] args) {
        SpringApplication.run(GeoDataApplication.class, args);
    }
}
