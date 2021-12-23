package com.distributed.airways.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.distributed.airways.entity.Flight;
import com.distributed.airways.service.FlightService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FlightResolver implements GraphQLResolver<Flight> {
    private final FlightService flightService;

    public List<String> category(Flight flight) {
        return flightService.getPriceCategories();
    }

    public List<Double> price(Flight flight) {
        return flightService.getPrices(flight);
    }
}
