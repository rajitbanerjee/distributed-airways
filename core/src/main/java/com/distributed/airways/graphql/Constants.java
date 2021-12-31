package com.distributed.airways.graphql;

import java.util.Arrays;
import java.util.List;

public class Constants {
    public static final String SOURCE_CITIES_QUERY = "query SourceCities { sourceCities }";
    public static final String DESTINATION_CITIES_QUERY =
            "query DestinationCities { destinationCities }";
    public static final String FLIGHTS_QUERY =
            "query Flights ($date: String, $sourceCity: String, $destinationCity: String) { flights"
                    + " (date: $date, sourceCity: $sourceCity, destinationCity: $destinationCity) { id"
                    + " airline flightNumber time sourceCity destinationCity sourceAirport"
                    + " destinationAirport dayOfWeek price category }}";

    public static final String BROKER_PORT = "8080";
    public static final String UI_CLIENT = "http://localhost:3000";
    public static final List<String> AIRLINE_HOSTS =
            Arrays.asList(
                    "american-airlines:8081",
                    "cathay-pacific:8082",
                    "emirates:8083",
                    "ryan-air:8084");
}
