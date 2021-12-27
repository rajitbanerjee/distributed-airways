package com.distributed.airways.service;

import com.distributed.airways.model.EmiratesFlight;
import com.distributed.airways.repository.FlightRepository;
import com.distributed.airways.utils.DateFormatter;
import com.google.common.collect.ImmutableMap;
import graphql.schema.DataFetcher;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class EmiratesService {

    private List<EmiratesFlight> flights;
    private static final Map<String, Integer> TICKET_CLASS_PREMIUMS =
            ImmutableMap.of("Economy", 1, "Business", 5, "First", 8);

    public DataFetcher<List<EmiratesFlight>> getFlightsDataFetcher(
            FlightRepository flightRepository) throws IOException {
        return dataFetchingEnvironment -> {
            String date = dataFetchingEnvironment.getArgument("date");
            String sourceCity = dataFetchingEnvironment.getArgument("sourceCity");
            String destinationCity = dataFetchingEnvironment.getArgument("destinationCity");
            String dayOfWeek = DateFormatter.dateToDayOfWeek(date);

            flights = flightRepository.findFlights(dayOfWeek, sourceCity, destinationCity);
            updateTicketPrices();
            return flights;
        };
    }

    private void updateTicketPrices() {
        applyTicketClassPremiums();
        // TODO? using utils from core here, or just post-processing in broker?
        // applyWeekendPremiums();
        // applySeasonalPremiums();
    }

    private void applyTicketClassPremiums() {
        for (EmiratesFlight flight : flights) {
            List<Double> prices = flight.getPrice();
            Double economyPrice = prices.remove(0);

            for (String ticketClass : flight.getCategory()) {
                prices.add(economyPrice * TICKET_CLASS_PREMIUMS.getOrDefault(ticketClass, 1));
            }

            flight.setPrice(prices);
        }
    }
}
