package com.distributed.airways.service;

import com.distributed.airways.model.RyanAirFlight;
import com.distributed.airways.repository.RyanAirRepository;
import com.distributed.airways.utils.DateFormatter;
import graphql.schema.DataFetcher;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class RyanAirService {
    private List<RyanAirFlight> flights;
    private RyanAirRepository flightRepository;

    private static final String[] CLASSES = {"Value", "Regular", "Plus"};
    private static final int[] FARES = {1, 4, 6};

    public RyanAirService(RyanAirRepository flighRepository) {
        this.flightRepository = flighRepository;
    }

    public DataFetcher<List<RyanAirFlight>> getFlightsDataFetcher(
            RyanAirRepository flightRepository) throws IOException {
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
        applyTicketTypePremiums();
        // TODO? using utils from core here, or just post-processing in broker?
        // applyWeekendPremiums();
        // applySeasonalPremiums();
    }

    private void applyTicketTypePremiums() {
        for (RyanAirFlight flight : flights) {
            List<Double> prices = flight.getPrice();
            Double valuePrice = prices.remove(0);

            for (String ticketClass : flight.getCategory()) {
                int multiplier =
                        (int) Array.get(FARES, Arrays.asList(CLASSES).indexOf(ticketClass));
                prices.add(valuePrice * multiplier);
            }

            flight.setPrice(prices);
        }
    }

    public void saveFlights(List<RyanAirFlight> flights) {
        flightRepository.saveAll(flights);
    }

    public void deleteFlights() {
        flightRepository.deleteAll();
    }
}
