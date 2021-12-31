package com.distributed.airways;

import com.distributed.airways.model.RyanAirFlight;
import com.distributed.airways.service.RyanAirService;
import com.distributed.airways.utils.FileIO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        System.out.println("Starting airline service: Ryanair");
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner runner(RyanAirService flightService) {
        return args -> {
            try {
                List<RyanAirFlight> flights;
                Gson gson = new Gson();
                String flightsString = FileIO.readFileAsString("data/flights.json");
                Type listType = new TypeToken<List<RyanAirFlight>>() {}.getType();
                flights = gson.fromJson(flightsString, listType);
                flightService.deleteFlights();
                flightService.saveFlights(flights);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}
