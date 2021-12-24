package com.distributed.airways.resolver;

import com.distributed.airways.service.FlightService;
import com.distributed.airways.utils.DateFormatter;
import org.springframework.stereotype.Component;

import java.util.List;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.distributed.airways.model.Flight;
import com.distributed.airways.repository.FlightRepository;

// import com.distributed.airways.service.CathayPacific;
import java.text.ParseException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FlightQuery implements GraphQLQueryResolver {
    private final FlightRepository repo;
    private final FlightService flightService;

    public List<Flight> availableFlightsAll(String date, String sourceCity, String destinationCity) {
        String dayOfWeek = "";
        try {
            dayOfWeek = DateFormatter.dateToDayOfWeek(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Flight> flights = repo.findFlights(dayOfWeek, sourceCity, destinationCity);
        flightService.updateTicketPrice(flights);
        return flights;
    }
}
