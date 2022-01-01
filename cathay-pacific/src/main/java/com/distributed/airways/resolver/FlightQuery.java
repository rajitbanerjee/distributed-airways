package com.distributed.airways.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.distributed.airways.model.CathayFlight;
import com.distributed.airways.repository.CathayRepository;
import com.distributed.airways.service.CathayService;
import com.distributed.airways.utils.DateFormatter;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FlightQuery implements GraphQLQueryResolver {
    private final CathayRepository flightRepository;
    private final CathayService flightService;

    public List<String> sourceCities() {
        return flightRepository.findAll().stream()
                .map(flight -> flight.getSourceCity())
                .collect(Collectors.toList());
    }

    public List<String> destinationCities() {
        return flightRepository.findAll().stream()
                .map(flight -> flight.getDestinationCity())
                .collect(Collectors.toList());
    }

    public List<CathayFlight> flights(String date, String sourceCity, String destinationCity) {
        String dayOfWeek = "";
        try {
            dayOfWeek = DateFormatter.dateToDayOfWeek(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<CathayFlight> flights =
                flightRepository.findFlights(dayOfWeek, sourceCity, destinationCity);
        flightService.updateTicketPrice(flights);
        return flights;
    }
}
