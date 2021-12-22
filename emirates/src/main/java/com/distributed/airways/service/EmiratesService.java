package com.distributed.airways.service;

import com.distributed.airways.model.EmiratesFlight;
import com.distributed.airways.repository.FlightRepository;
import graphql.schema.DataFetcher;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class EmiratesService {
    private List<EmiratesFlight> flights;
    private static final String BUSINESS_CLASS = "Business";
    private static final String FIRST_CLASS = "First";
    private static final int BUSINESS_PREMIUM = 5;
    private static final int FIRST_PREMIUM = 8;

    public DataFetcher<List<EmiratesFlight>> getFlightsDataFetcher(
            FlightRepository flightRepository) throws IOException {
        return dataFetchingEnvironment -> {
            String dayOfWeek = dataFetchingEnvironment.getArgument("dayOfWeek");
            String sourceCity = dataFetchingEnvironment.getArgument("sourceCity");
            String destinationCity = dataFetchingEnvironment.getArgument("destinationCity");

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
            Double economyPrice = prices.get(0);

            for (String ticketClass : flight.getCategory()) {
                if (ticketClass.equals(BUSINESS_CLASS)) {
                    prices.add(economyPrice * BUSINESS_PREMIUM);
                } else if (ticketClass.equals(FIRST_CLASS)) {
                    prices.add(economyPrice * FIRST_PREMIUM);
                }
            }

            flight.setPrice(prices);
        }
    }
}
