package com.distributed.airways.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.distributed.airways.model.CathayFlight;
import com.distributed.airways.repository.CathayRepository;
import com.distributed.airways.service.CathayService;
import com.distributed.airways.utils.DateFormatter;
import java.text.ParseException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FlightQuery implements GraphQLQueryResolver {
    private final CathayRepository repo;
    private final CathayService flightService;

    public List<CathayFlight> flights(String date, String sourceCity, String destinationCity) {
        String dayOfWeek = "";
        try {
            dayOfWeek = DateFormatter.dateToDayOfWeek(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<CathayFlight> flights = repo.findFlights(dayOfWeek, sourceCity, destinationCity);
        flightService.updateTicketPrice(flights);
        return flights;
    }
}
