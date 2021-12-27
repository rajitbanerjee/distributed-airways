package com.distributed.airways.graphql;

import java.util.Arrays;
import java.util.List;

public class Constants {
    public static final String FLIGHTS_QUERY =
            "query Flights ($date: String, $sourceCity: String, $destinationCity: String) { flights"
                    + " (date: $date, sourceCity: $sourceCity, destinationCity: $destinationCity) { id"
                    + " airline flightNumber time sourceCity destinationCity sourceAirport"
                    + " destinationAirport dayOfWeek price category }}";

    public static final String BROKER_PORT = "8080";

    public static final List<String> AIRLINE_HOSTS =
            Arrays.asList(
                    "american-airlines:8081",
                    "ryan-air:8082",
                    "emirates:8083",
                    "cathay-pacific:8084");
}
