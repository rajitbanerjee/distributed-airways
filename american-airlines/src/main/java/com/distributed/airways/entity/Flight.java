package com.distributed.airways.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("Flight")
@Data
@AllArgsConstructor
public class Flight implements Serializable {
    @Id private String id;
    private String airline;
    private List<String> flightNumber;
    private List<String> time;
    @Indexed private String sourceCity;
    @Indexed private String destinationCity;
    private String sourceAirport;
    private String destinationAirport;
    @Indexed private Set<String> dayOfWeek;
    private double basePrice;
}
