package com.distributed.airways.repository;

import com.distributed.airways.entity.Flight;
import java.util.List;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends CrudRepository<Flight, String> {
    @NotNull
    List<Flight> findAll();

    List<Flight> findFlightBySourceCityAndDestinationCityAndDayOfWeek(
            String sourceCity, String destinationCity, Set<String> dayOfWeek);

    List<Flight> findFlightBySourceCityAndDestinationCity(
            String sourceCity, String destinationCity);
}
