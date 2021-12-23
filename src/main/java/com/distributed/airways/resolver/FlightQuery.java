package com.distributed.airways.resolver;

import org.springframework.stereotype.Component;

import java.util.List;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.distributed.airways.model.Flight;
import com.distributed.airways.repository.FlightRepository;
import com.distributed.airways.service.CathayPacific;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FlightQuery implements GraphQLQueryResolver {
    private final FlightRepository repo;
    private final CathayPacific CPService;

    public List<Flight> availableFlights(String sourceCity) {
        return repo.findbyCity(sourceCity);
    }

    public List<Flight> availableFlightsAll(String sourceCity, String destinationCity, String dayOfWeek) {
        List<Flight> flights = repo.findbySourceDestinationDay(sourceCity, destinationCity, dayOfWeek);
        CPService.applyTicketClassPremiums(flights);
        return flights;
    }

    public Iterable<Flight> allFlights() {
        return repo.findAll();
    }
}
