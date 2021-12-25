package com.distributed.airways.model;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@Entity
@Getter
@Setter
@Table(name = "Flight")
@TypeDef(name = "list-array", typeClass = ListArrayType.class)
public class CathayFlight {
    @Id private String id;
    private String airline;

    @Type(type = "list-array")
    @Column(name = "flightnumber", columnDefinition = "text[]")
    private List<String> flightNumber;

    @Type(type = "list-array")
    @Column(name = "time", columnDefinition = "text[]")
    private List<String> time;

    @Column(name = "sourcecity")
    private String sourceCity;

    @Column(name = "destinationcity")
    private String destinationCity;

    @Column(name = "sourceairport")
    private String sourceAirport;

    @Column(name = "destinationairport")
    private String destinationAirport;

    @Type(type = "list-array")
    @Column(name = "dayofweek", columnDefinition = "text[]")
    private List<String> dayOfWeek;

    @Type(type = "list-array")
    @Column(name = "price", columnDefinition = "float[]")
    private List<Double> price;

    @Type(type = "list-array")
    @Column(name = "category", columnDefinition = "text[]")
    private List<String> category;
}
