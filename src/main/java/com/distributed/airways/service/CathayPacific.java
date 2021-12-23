package com.distributed.airways.service;

import java.util.List;

import com.distributed.airways.model.Flight;

import org.springframework.stereotype.Service;

@Service
public class CathayPacific {
    public void applyTicketClassPremiums(List<Flight> flights) {
        for (Flight f : flights) {
            if (f.getCategory().equals("Business")) {
                System.out.println("HELLOOOO2O");
                f.setPrice(f.getPrice() + 50);
            } else if (f.getCategory().equals("Premium")) {
                System.out.println("HELLOOOO3O");
                f.setPrice(f.getPrice() + 100);
            }
        }
    }
}
