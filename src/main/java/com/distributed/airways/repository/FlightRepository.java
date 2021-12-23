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

    @Query("SELECT flight FROM Flight flight WHERE flight.sourceCity=(:sCity)")
    List<Flight> findbyCity(@Param("sCity") String sourceCity);

    @Query("SELECT flight FROM Flight flight WHERE flight.sourceCity=(:sCity) AND flight.destinationCity=(:dCity) AND flight.dayOfWeek=(:day)")
    List<Flight> findbySourceDestinationDay(@Param("sCity") String sourceCity, @Param("dCity") String destinationCity,
            @Param("day") String dayOfWeek);

}
