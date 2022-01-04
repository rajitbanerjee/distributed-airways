package com.distributed.airways.service;

import com.distributed.airways.entity.Flight;
import com.distributed.airways.repository.FlightRepository;
import com.distributed.airways.utils.FileIO;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FlightService {
    private static final String BASE_PATH = "data";
    private static final Map<String, Double> DISCOUNTS =
            ImmutableMap.of("None", 0.0, "Silver", 0.1, "Gold", 0.2, "Platinum", 0.3);
    private final FlightRepository flightRepository;
    private final Gson gson;

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
        List<Flight> query =
                flightRepository.findFlightBySourceCityAndDestinationCity(
                        sourceCity, destinationCity);
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

    @Transactional(readOnly = true)
    public List<String> getSourceCities() {
        return flightRepository.findAll().stream()
                .map(Flight::getSourceCity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<String> getDestinationCities() {
        return flightRepository.findAll().stream()
                .map(Flight::getDestinationCity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void initDatabase() {
        List<Flight> flights = new ArrayList<>();
        try {
            String jsonString = FileIO.readFileAsString(BASE_PATH + "/flight.json");
            Type listType = new TypeToken<List<Flight>>() {}.getType();
            flights = gson.fromJson(jsonString, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        flightRepository.deleteAll();
        flightRepository.saveAll(flights);
    }
}
