package com.distributed.airways.service;

import com.distributed.airways.entity.Flight;
import com.distributed.airways.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {
    private static final String AIRLINE_NAME = "American Airlines";
    private static final List<String> CATEGORIES = Arrays.asList("Silver", "Gold", "Platinum");
    private static final Flight[] flights = {
            new Flight("1", AIRLINE_NAME, "AA11", Arrays.asList("08:00", "09:00"), "Dublin", "London", "DUB", "LHR", "Monday", Collections.singletonList(100.1), CATEGORIES, Arrays.asList(0.1, 0.2, 0.3)),
            new Flight("2", AIRLINE_NAME, "AA12", Arrays.asList("09:00", "10:00"), "Dublin", "London", "DUB", "LHR", "Tuesday", Collections.singletonList(123.3), CATEGORIES, Arrays.asList(0.0, 0.1, 0.2)),
            new Flight("3", AIRLINE_NAME, "AA13", Arrays.asList("08:00", "11:00"), "London", "Madrid", "LHR", "MAD", "Tuesday", Collections.singletonList(50.1), CATEGORIES, Arrays.asList(0.3, 0.4, 0.5))};

    private final FlightRepository flightRepository;

    @Transactional(readOnly = true)
    public Flight getFlightById(String id) {
        return flightRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Flight> getFlights(String dayOfWeek, String sourceCity, String destinationCity) {
        return flightRepository.findFlightsByDayOfWeekAndSourceCityAndDestinationCity(dayOfWeek, sourceCity, destinationCity);
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
