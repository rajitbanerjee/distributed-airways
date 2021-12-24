package com.distributed.airways.service;

import com.distributed.airways.model.RyanAirFlight;
import com.distributed.airways.repository.FlightRepository;
import com.distributed.airways.utils.DateFormatter;
import graphql.schema.DataFetcher;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class RyanAirService {
    private List<RyanAirFlight> flights;
    private static final String REGULAR_CLASS = "Regular";
    private static final String PLUS_CLASS = "Plus";
    private static final int REGULAR_PREMIUM = 5;
    private static final int PLUS_PREMIUM = 8;

    public DataFetcher<List<RyanAirFlight>> getFlightsDataFetcher(FlightRepository flightRepository)
            throws IOException {
        return dataFetchingEnvironment -> {
            String date = dataFetchingEnvironment.getArgument("date");
            String sourceCity = dataFetchingEnvironment.getArgument("sourceCity");
            String destinationCity = dataFetchingEnvironment.getArgument("destinationCity");
            String dayOfWeek = DateFormatter.dateToDayOfWeek(date);

            flights =
                    flightRepository.findbyKeySourceCityAndDestinationCity(
                            dayOfWeek, sourceCity, destinationCity);
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
        for (RyanAirFlight flight : flights) {
            List<Double> prices = flight.getPrice();
            Double valuePrice = prices.get(0);

            for (String ticketClass : flight.getCategory()) {
                if (ticketClass.equals(REGULAR_CLASS)) {
                    prices.add(valuePrice * REGULAR_PREMIUM);
                } else if (ticketClass.equals(PLUS_CLASS)) {
                    prices.add(valuePrice * PLUS_PREMIUM);
                }
            }

            flight.setPrice(prices);
        }
    }
}
