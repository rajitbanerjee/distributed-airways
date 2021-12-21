package com.distributed.airways.graphql;

import com.distributed.airways.entity.Flight;
// import com.google.common.base.Charsets;
// import com.google.common.io.Resources;
// import com.jsoniter.JsonIterator;
// import com.jsoniter.spi.TypeLiteral;

import graphql.schema.DataFetcher;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GraphQLDataFetchers {
    private static List<Flight> flights =
            Arrays.asList(
                    new Flight(
                            "1",
                            "Emirates",
                            Arrays.asList("EK162", "EK570"),
                            Arrays.asList("12:55", "00:30", "02:00", "07:40"),
                            "Dublin",
                            "Kolkata",
                            "DUB",
                            "CCU",
                            Arrays.asList(
                                    "Monday",
                                    "Tuesday",
                                    "Wednesday",
                                    "Thursday",
                                    "Friday",
                                    "Saturday",
                                    "Sunday"),
                            Arrays.asList(500.00)));
    private static final String DATA_FILE = "flights.json";

    // private static void parseJSON(String file) throws IOException {
    //     // String jsonData = Resources.toString(Resources.getResource(file), Charsets.UTF_8);
    //     // flights = JsonIterator.deserialize(jsonData, new TypeLiteral<List<Flight>>() {});
    //     flights.add(
    //             new Flight(
    //                     "1",
    //                     "Emirates",
    //                     Arrays.asList("EK162", "EK570"),
    //                     Arrays.asList("12:55", "00:30", "02:00", "07:40"),
    //                     "Dublin",
    //                     "Kolkata",
    //                     "DUB",
    //                     "CCU",
    //                     Arrays.asList(
    //                             "Monday",
    //                             "Tuesday",
    //                             "Wednesday",
    //                             "Thursday",
    //                             "Friday",
    //                             "Saturday",
    //                             "Sunday"),
    //                     Arrays.asList(500.00)));

    //     // "id": "flight1",
    //     // "airline": "Emirates",
    //     // "flightNumber": ["EK162", "EK570"],
    //     // "time": ["12:55", "00:30", "02:00", "07:40"],
    //     // "sourceCity": "Dublin",
    //     // "destinationCity": "Kolkata",
    //     // "sourceAirport": "DUB",
    //     // "destinationAirport": "CCU",
    //     // "dayOfWeek": ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday",
    //     // "Sunday"],
    //     // "price": ["500"]
    // }

    public DataFetcher<List<Flight>> getFlightsDataFetcher() throws IOException {
        // TODO move database population to Application.java
        // parseJSON(DATA_FILE);

        return dataFetchingEnvironment -> {
            String dayOfWeek = dataFetchingEnvironment.getArgument("dayOfWeek");
            String sourceCity = dataFetchingEnvironment.getArgument("sourceCity");
            String destinationCity = dataFetchingEnvironment.getArgument("destinationCity");
            return flights.stream()
                    .filter(
                            flight ->
                                    flight.getDayOfWeek().contains(dayOfWeek)
                                            && flight.getSourceCity().equals(sourceCity)
                                            && flight.getDestinationCity().equals(destinationCity))
                    .collect(Collectors.toList());
        };
    }
}
