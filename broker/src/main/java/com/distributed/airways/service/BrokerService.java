package com.distributed.airways.service;

import com.distributed.airways.graphql.Constants;
import com.distributed.airways.graphql.GraphQLQuery;
import com.distributed.airways.graphql.GraphQLResponse;
import com.distributed.airways.model.Flight;
import com.google.common.collect.ImmutableMap;
import graphql.schema.DataFetcher;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BrokerService {

    public DataFetcher<List<Flight>> getFlightsDataFetcher() throws IOException {
        return dataFetchingEnvironment -> {
            String date = dataFetchingEnvironment.getArgument("date");
            String sourceCity = dataFetchingEnvironment.getArgument("sourceCity");
            String destinationCity = dataFetchingEnvironment.getArgument("destinationCity");
            return getFlightsFromAirlines(date, sourceCity, destinationCity);
        };
    }

    private List<Flight> getFlightsFromAirlines(
            String date, String sourceCity, String destinationCity) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> variables =
                ImmutableMap.of(
                        "date", date, "sourceCity", sourceCity, "destinationCity", destinationCity);
        GraphQLQuery body = new GraphQLQuery(Constants.FLIGHTS_QUERY, variables);
        HttpEntity<GraphQLQuery> request = new HttpEntity<>(body);
        List<Flight> flights = new ArrayList<>();

        // Retrieve flights from each individual airline service
        for (String airlineHost : Constants.AIRLINE_HOSTS) {
            String url = "http://" + airlineHost + "/graphql";
            GraphQLResponse response =
                    restTemplate.postForObject(url, request, GraphQLResponse.class);
            List<Flight> dataFlights = response.getData().get("flights");
            if (!dataFlights.isEmpty()) {
                flights.addAll(dataFlights);
            }
        }
        return flights;
    }
}
