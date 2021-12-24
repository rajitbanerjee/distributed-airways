package com.distributed.airways.repository;

import com.distributed.airways.model.RyanAirFlight;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface FlightRepository extends MongoRepository<RyanAirFlight, String> {

    @Query("{$and: [{dayOfWeek: '?0'}, {sourceCity: '?1'}, {destinationCity: '?2'}]}")
    List<RyanAirFlight> findFlights(String dayOfWeek, String sourceCity, String destinationCity);
}
