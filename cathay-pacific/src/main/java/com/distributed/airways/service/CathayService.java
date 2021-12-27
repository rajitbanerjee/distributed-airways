package com.distributed.airways.service;

import com.distributed.airways.model.CathayFlight;
import com.distributed.airways.repository.CathayRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CathayService {

    private CathayRepository flightRepository;

    public CathayService(CathayRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public void save(List<CathayFlight> flights) {
        flightRepository.saveAll(flights);
    }

    public void updateTicketPrice(List<CathayFlight> flights) {
        for (CathayFlight flight : flights) {
            List<Double> prices = flight.getPrice();
            Double basePrice = prices.get(0);

            for (String category : flight.getCategory()) {
                if (category.equals("Business")) {
                    prices.add(basePrice * 5);
                } else if (category.equals("First")) {
                    prices.add(basePrice * 8);
                }
            }
            flight.setPrice(prices);
        }
    }
}
