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
            //ImmutableMap.of("id", "flight1",
            //        "flightNumber", "FR282",
            //        "time", "18:00 - 19:00",
            //        "sourceCity", "Dublin",
            //        "destCity", "London",
            //        "sourceAirport", "DUB",
            //        "destAirport", "GTA",
            //        "day", "Wednesday",
            //        "price", "10",
            //        "baggage", "normal"),
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
                    .build()
        //     ImmutableMap.of("id", "flight2",
                //     "flightNumber", "FR382",
                //     "time", "12:00 - 13:00",
                //     "sourceCity", "Dublin",
                //     "destCity", "London",
                //     "sourceAirport", "DUB",
                //     "destAirport", "GTA",
                //     "day", "Monday",
                //     "price", "12",
                //     "baggage", "normal"),
        //     ImmutableMap.of("id", "flight3",
        //             "flightNumber", "FR283",
        //             "time", "22:00 - 23:00",
        //             "sourceCity", "Dublin",
        //             "destCity", "London",
        //             "sourceAirport", "DUB",
        //             "destAirport", "GTA",
        //             "day", "Wednesday",
        //             "price", "23",
        //             "baggage", "normal")
    );

    public DataFetcher getFlightByDestination() {
        return dataFetchingEnvironment -> {
            String flightSrcCity = dataFetchingEnvironment.getArgument("sourceCity");
            String flightDestCity = dataFetchingEnvironment.getArgument("destCity");
            return flights
                    .stream()
                    .filter(flight -> flight.get("destCity").equals(flightDestCity) && flight.get("sourceCity").equals(flightSrcCity))
                    .findFirst()
                    .orElse(null);

        };
    }
}