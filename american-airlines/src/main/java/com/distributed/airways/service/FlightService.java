package com.distributed.airways.service;

import com.distributed.airways.entity.Flight;
import com.distributed.airways.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;

    @Transactional(readOnly = true)
    public Flight getFlightById(String id) {
        return flightRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Flight> getFlights() {
        return flightRepository.findAll();
    }
}
