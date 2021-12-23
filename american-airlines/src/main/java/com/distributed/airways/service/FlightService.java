package com.distributed.airways.service;

import com.distributed.airways.entity.Flight;
import com.distributed.airways.repository.FlightRepository;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;
    private static final String AIRLINE_NAME = "American Airlines";
    private static final Map<String, Double> DISCOUNTS = ImmutableMap.of("None", 0.0, "Silver", 0.1, "Gold", 0.2, "Platinum", 0.3);
    private static final Flight[] flights = new Flight[]{
            new Flight("1", AIRLINE_NAME, Collections.singletonList("AA0011"), Arrays.asList("08:00", "09:00"), "Dublin", "London", "DUB", "LHR", ImmutableSet.of("Monday", "Friday"), 100),
            new Flight("2", AIRLINE_NAME, Collections.singletonList("AA0012"), Arrays.asList("09:00", "10:00"), "Dublin", "London", "DUB", "LHR", ImmutableSet.of("Monday", "Thursday"), 200),
            new Flight("3", AIRLINE_NAME, Collections.singletonList("AA0012"), Arrays.asList("08:00", "11:00"), "London", "Madrid", "LHR", "MAD", ImmutableSet.of("Tuesday", "Friday"), 300)};

    public List<Double> getPrices(Flight flight) {
        List<Double> prices = new ArrayList<>();
        double basePrice = flight.getBasePrice();
        for (Map.Entry<String, Double> discount : DISCOUNTS.entrySet()) {
            prices.add(basePrice * (1 - discount.getValue()));
        }
        return prices;
    }

    public List<String> getPriceCategories() {
        return new ArrayList<>(DISCOUNTS.keySet());
    }

    @Transactional(readOnly = true)
    public Flight getFlightById(String id) {
        return flightRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Flight> getFlights(String dayOfWeek, String sourceCity, String destinationCity) {
        List<Flight> query = flightRepository.findFlightBySourceCityAndDestinationCity(sourceCity, destinationCity);
        List<Flight> result = new ArrayList<>();
        for (Flight flight : query) {
            if (flight.getDayOfWeek().contains(dayOfWeek)) {
                result.add(flight);
            }
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<Flight> getFlights() {
        return flightRepository.findAll();
    }

    @Transactional
    public void initDatabase() {
        flightRepository.deleteAll();
        flightRepository.saveAll(Arrays.asList(flights));
    }
}
