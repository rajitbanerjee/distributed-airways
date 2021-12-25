package com.distributed.airways.repository;

import com.distributed.airways.model.CathayFlight;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface CathayRepository extends JpaRepository<CathayFlight, String> {

    @Query(
            value =
                    "select * FROM Flight flight WHERE flight.sourcecity=(:sCity) AND flight.destinationcity=(:dCity) AND (:day)= any(flight.dayofweek)",
            nativeQuery = true)
    List<CathayFlight> findFlights(
            @Param("day") String dayOfWeek,
            @Param("sCity") String sourceCity,
            @Param("dCity") String destinationCity);
}
