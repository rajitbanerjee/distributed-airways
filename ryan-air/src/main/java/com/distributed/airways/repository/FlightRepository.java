package com.distributed.airways.repository;

import com.distributed.airways.model.FlightKey;
import com.distributed.airways.model.RyanAirFlight;
import java.util.List;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends CassandraRepository<RyanAirFlight, FlightKey> {
    List<RyanAirFlight> findbyKeySourceCityAndDestinationCity(
            final String dayOfWeek, final String sourceCity, final String destinationCity);
}
