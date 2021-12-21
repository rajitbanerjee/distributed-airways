package com.distributed.airways.resolver;


import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.distributed.airways.entity.Flight;
import com.distributed.airways.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Query implements GraphQLQueryResolver {
    private final FlightService flightService;

    public List<Flight> flights(String dayOfWeek, String sourceCity, String destinationCity) {
        return flightService.getFlights(dayOfWeek, sourceCity, destinationCity);
    }

    public List<Flight> allFlights() {
        return flightService.getFlights();
    }

    public Flight flightById(String id) {
        return flightService.getFlightById(id);
    }
}
