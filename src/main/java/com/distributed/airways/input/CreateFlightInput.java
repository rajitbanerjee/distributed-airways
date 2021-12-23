package com.distributed.airways.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateFlightInput {
    private String id;
    private String airline;
    private String flightNumber;
    private String time;
    private String sourceCity;
    private String destinationCity;
    private String sourceAirport;
    private String destinationAirport;
    private String dayOfWeek;
    private Float price;
    private String category;

}