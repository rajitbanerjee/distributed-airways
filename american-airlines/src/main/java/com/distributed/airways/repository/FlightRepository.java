package com.distributed.airways.repository;

import com.distributed.airways.entity.Flight;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends CrudRepository<Flight, String> {
    @NotNull
    List<Flight> findAll();
}
