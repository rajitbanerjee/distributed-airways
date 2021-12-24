package com.distributed.airways.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flight {
    private String id;
    private String airline;
    private List<String> flightNumber;
    private List<String> time;
    private String sourceCity;
    private String destinationCity;
    private String sourceAirport;
    private String destinationAirport;
    private List<String> dayOfWeek;
    private List<Double> price;
    private List<String> category;
}
