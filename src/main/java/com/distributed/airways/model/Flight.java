package com.distributed.airways.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Flight")
public class Flight implements Serializable {
    @Id
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
