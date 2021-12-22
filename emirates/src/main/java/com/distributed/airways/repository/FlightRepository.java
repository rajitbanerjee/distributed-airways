package com.distributed.airways.repository;

import com.distributed.airways.model.EmiratesFlight;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface FlightRepository extends MongoRepository<EmiratesFlight, String> {

    @Query("{$and: [{dayOfWeek: '?0'}, {sourceCity: '?1'}, {destinationCity: '?2'}]}")
    List<EmiratesFlight> findFlights(String dayOfWeek, String sourceCity, String destinationCity);
}
