package com.distributed.airways.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import javax.transaction.Transactional;

import com.distributed.airways.model.Flight;

@Repository
@Transactional
public interface FlightRepository extends JpaRepository<Flight, String> {

    @Query(value = "select * FROM Flight flight WHERE flight.sourcecity=(:sCity) AND flight.destinationcity=(:dCity) AND (:day)= any(flight.dayofweek)", nativeQuery = true)
    List<Flight> findFlights(
            @Param("day") String dayOfWeek,
            @Param("sCity") String sourceCity,
            @Param("dCity") String destinationCity);

}
