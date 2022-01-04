package com.distributed.airways.service;

import com.distributed.airways.model.RyanAirFlight;
import com.distributed.airways.repository.RyanAirRepository;
import com.distributed.airways.utils.DateFormatter;
import graphql.schema.DataFetcher;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class RyanAirService {
    private List<RyanAirFlight> flights;
    private RyanAirRepository flightRepository;

    private static final String[] BAGGAGE_CLASSES = {"Value", "Regular", "Plus"};
    private static final int[] FARES = {1, 4, 6};

    public RyanAirService(RyanAirRepository flighRepository) {
        this.flightRepository = flighRepository;
    }

    public DataFetcher<List<String>> getSourceCitiesDataFetcher(
            RyanAirRepository flightRepository) {
        return dataFetchingEnvironment -> {
            return flightRepository.findAll().stream()
                    .map(flight -> flight.getSourceCity())
                    .collect(Collectors.toList());
        };
    }

    public DataFetcher<List<String>> getDestinationCitiesDataFetcher(
            RyanAirRepository flightRepository) {
        return dataFetchingEnvironment -> {
            return flightRepository.findAll().stream()
                    .map(flight -> flight.getDestinationCity())
                    .collect(Collectors.toList());
        };
    }

    public DataFetcher<List<RyanAirFlight>> getFlightsDataFetcher(
            RyanAirRepository flightRepository) throws IOException {
        return dataFetchingEnvironment -> {
            String date = dataFetchingEnvironment.getArgument("date");
            String sourceCity = dataFetchingEnvironment.getArgument("sourceCity");
            String destinationCity = dataFetchingEnvironment.getArgument("destinationCity");
            String dayOfWeek = DateFormatter.dateToDayOfWeek(date);

            flights = flightRepository.findFlights(dayOfWeek, sourceCity, destinationCity);
            applyBaggageClassPremiums();
            return flights;
        };
    }

    private void applyBaggageClassPremiums() {
        for (RyanAirFlight flight : flights) {
            List<Double> prices = flight.getPrice();
            Double valuePrice = prices.remove(0);

            for (String baggageClass : flight.getCategory()) {
                int multiplier =
                        (int)
                                Array.get(
                                        FARES,
                                        Arrays.asList(BAGGAGE_CLASSES).indexOf(baggageClass));
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
