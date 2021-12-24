package com.distributed.airways.service;

import com.distributed.airways.graphql.Constants;
import com.distributed.airways.graphql.GraphQLQuery;
import com.distributed.airways.graphql.GraphQLResponse;
import com.distributed.airways.model.Flight;
import com.google.common.collect.ImmutableMap;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import graphql.schema.DataFetcher;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Service
@RestController
public class BrokerService {

    @Autowired private RestTemplate restTemplate;
    @Autowired private EurekaClient discoveryClient;
    private static final String AIRLINE_SERVICE_APP_NAME_PREFIX = "airline-service";

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
        // Form HTTP request to send to airline services
        Map<String, String> variables =
                ImmutableMap.of(
                        "date", date, "sourceCity", sourceCity, "destinationCity", destinationCity);
        GraphQLQuery body = new GraphQLQuery(Constants.FLIGHTS_QUERY, variables);
        HttpEntity<GraphQLQuery> request = new HttpEntity<>(body);
        List<Flight> flights = new ArrayList<>();

        // Retrieve flights from each registered airline service
        for (Application app : discoveryClient.getApplications().getRegisteredApplications()) {
            String appName = app.getName().toLowerCase();
            if (!appName.startsWith(AIRLINE_SERVICE_APP_NAME_PREFIX)) {
                continue;
            }
            String url = "http://" + appName + "/graphql";
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
