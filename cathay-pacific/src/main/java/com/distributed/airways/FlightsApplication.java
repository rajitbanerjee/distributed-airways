package com.distributed.airways;

import com.distributed.airways.model.Flight;
import com.distributed.airways.service.FlightService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SpringBootApplication
public class FlightsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightsApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(FlightService flightService) {
        return args -> {
            // read JSON and load json
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Flight>> typeReference = new TypeReference<List<Flight>>() {
            };
            InputStream inputStream = TypeReference.class.getResourceAsStream("/data/flights.json");
            try {
                List<Flight> flights = mapper.readValue(inputStream, typeReference);
                flightService.save(flights);
                System.out.println("Flights Saved!");
            } catch (IOException e) {
                System.out.println("Unable to save flights: " + e.getMessage());
            }
        };
    }
}
