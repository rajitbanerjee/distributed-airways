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

    @Column @Getter @Setter private String id;

    @Column @Getter @Setter private String airline;

    @Column @Getter @Setter private List<String> flightNumber;

    @Column @Getter @Setter private List<String> time;

    @Column @Getter @Setter private String sourceAirport;

    @Column @Getter @Setter private String destinationAirport;

    @Column @Getter @Setter private List<Double> price;

    @Column @Getter @Setter private List<String> category;
}
