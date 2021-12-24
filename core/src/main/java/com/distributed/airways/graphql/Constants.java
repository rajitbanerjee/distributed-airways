package com.distributed.airways.graphql;

public class Constants {
    public static final String FLIGHTS_QUERY =
            "query Flights ($date: String, $sourceCity: String, $destinationCity: String) { flights"
                    + " (date: $date, sourceCity: $sourceCity, destinationCity: $destinationCity) { id"
                    + " airline flightNumber time sourceCity destinationCity sourceAirport"
                    + " destinationAirport dayOfWeek price category }}";
}
