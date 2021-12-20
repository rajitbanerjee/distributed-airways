package books;
import com.google.common.collect.ImmutableMap;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class GraphQLDataFetchers {

    private static List<Map<String, String>> flights = Arrays.asList(
           ImmutableMap.<String, String>builder()
                    .put("id", "flight1")
                    .put("flightNumber", "FR282")
                    .put("time", "18:00 - 19:00")
                    .put("sourceCity", "Dublin")
                    .put("destCity", "London")
                    .put("sourceAirport", "DUB")
                    .put("destAirport", "GTA")
                    .put("day", "Wednesday")
                    .put("price", "10")
                    .put("baggage", "normal")
                    .build(),
           ImmutableMap.<String, String>builder()
                    .put("id", "flight2")
                    .put("flightNumber", "FR382")
                    .put("time", "12:00 - 13:00")
                    .put("sourceCity", "Dublin")
                    .put("destCity", "London")
                    .put("sourceAirport", "DUB")
                    .put("destAirport", "GTA")
                    .put("day", "Monday")
                    .put("price", "12")
                    .put("baggage", "normal")
                    .build(),
           ImmutableMap.<String, String>builder()
                    .put("id", "flight3")
                    .put("flightNumber", "FR283")
                    .put("time", "22:00 - 23:00")
                    .put("sourceCity", "Dublin")
                    .put("destCity", "London")
                    .put("sourceAirport", "DUB")
                    .put("destAirport", "GTA")
                    .put("day", "Wednesday")
                    .put("price", "23")
                    .put("baggage", "normal")
                    .build()                  
    );

    public DataFetcher<List<Map<String, String>>> getFlightByDestination() {
        return dataFetchingEnvironment -> {
            String flightSrcCity = dataFetchingEnvironment.getArgument("sourceCity");
            String flightDestCity = dataFetchingEnvironment.getArgument("destCity");
            return flights
                    .stream()
                    .filter(flight -> flight.get("destCity").equals(flightDestCity) && flight.get("sourceCity").equals(flightSrcCity))
                    .collect(Collectors.toList());

        };
    }
}