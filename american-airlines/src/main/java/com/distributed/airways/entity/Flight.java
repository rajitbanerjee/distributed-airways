package com.distributed.airways.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.util.List;

@RedisHash("Flight")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Flight implements Serializable {
    @Id
    private String id;
    private String airline;
    private String flightNumber;
    private List<String> time;
    @Indexed
    private String sourceCity;
    @Indexed
    private String destinationCity;
    private String sourceAirport;
    private String destinationAirport;
    @Indexed
    private String dayOfWeek;
    private List<Double> price;
    private List<String> category;
    private List<Double> discount;
}
