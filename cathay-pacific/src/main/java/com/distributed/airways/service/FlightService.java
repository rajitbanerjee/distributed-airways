package com.distributed.airways.service;

import com.distributed.airways.model.Flight;
import com.distributed.airways.repository.FlightRepository;
import com.distributed.airways.model.Flight;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {

    private FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public void save(List<Flight> flights) {
        flightRepository.saveAll(flights);
    }

    public void updateTicketPrice(List<Flight> flights) {
        for (Flight flight : flights) {
            List<Double> prices = flight.getPrice();
            Double basePrice = prices.get(0);

            for (String category : flight.getCategory()) {
                if (category.equals("Business")) {
                    prices.add(basePrice + 200);
                } else if (category.equals("First")) {
                    prices.add(basePrice + 400);
                }
            }
            flight.setPrice(prices);
        }
    }
}
