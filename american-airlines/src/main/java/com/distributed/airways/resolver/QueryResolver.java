package com.distributed.airways.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.distributed.airways.entity.Flight;
import com.distributed.airways.service.FlightService;
import com.distributed.airways.utils.DateFormatter;
import java.text.ParseException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueryResolver implements GraphQLQueryResolver {
    private final FlightService flightService;

    public List<String> sourceCities() {
        return flightService.getSourceCities();
    }

    public List<String> destinationCities() {
        return flightService.getDestinationCities();
    }

    public List<Flight> flights(String date, String sourceCity, String destinationCity) {
        String dayOfWeek = "";
        try {
            dayOfWeek = DateFormatter.dateToDayOfWeek(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return flightService.getFlights(dayOfWeek, sourceCity, destinationCity);
    }

    public List<Flight> allFlights() {
        return flightService.getFlights();
    }

    public Flight flightById(String id) {
        return flightService.getFlightById(id);
    }
}
