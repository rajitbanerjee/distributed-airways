package com.distributed.airways;

import com.distributed.airways.model.CathayFlight;
import com.distributed.airways.service.CathayService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FlightsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightsApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(CathayService flightService) {
        return args -> {
            // read JSON and load json
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<CathayFlight>> typeReference =
                    new TypeReference<List<CathayFlight>>() {};
            InputStream inputStream = TypeReference.class.getResourceAsStream("/data/flights.json");
            try {
                List<CathayFlight> flights = mapper.readValue(inputStream, typeReference);
                flightService.save(flights);
                System.out.println("Flights Saved!");
            } catch (IOException e) {
                System.out.println("Unable to save flights: " + e.getMessage());
            }
        };
    }
}
