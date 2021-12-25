package com.distributed.airways.model;

import java.util.List;
import lombok.*;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("flights_by_cities")
@Data
@AllArgsConstructor
public class RyanAirFlight {

    @PrimaryKey private FlightKey key;

    @Column private String id;

    @Column private String airline;

    @Column private List<String> flightNumber;

    @Column private List<String> time;

    @Column private String sourceAirport;

    @Column private String destinationAirport;

    @Column private List<Double> price;

    @Column private List<String> category;
}
