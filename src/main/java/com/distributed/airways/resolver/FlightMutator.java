package com.distributed.airways.resolver;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.distributed.airways.input.CreateFlightInput;
import com.distributed.airways.model.Flight;
import com.distributed.airways.repository.FlightRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class FlightMutator implements GraphQLMutationResolver {

    private final FlightRepository repo;

    public Flight createFlight(CreateFlightInput i) {
        Flight f = new Flight();
        f.setId(i.getId());
        f.setAirline(i.getAirline());
        f.setFlightNumber(i.getFlightNumber());
        f.setTime(i.getTime());
        f.setSourceCity(i.getSourceCity());
        f.setDestinationCity(i.getDestinationCity());
        f.setSourceAirport(i.getSourceAirport());
        f.setDestinationAirport(i.getDestinationAirport());
        f.setDayOfWeek(i.getDayOfWeek());
        f.setCategory(i.getCategory());
        f.setPrice(i.getPrice());
        return repo.save(f);
    }

}
