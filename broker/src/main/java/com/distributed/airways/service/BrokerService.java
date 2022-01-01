package com.distributed.airways.service;

import com.distributed.airways.graphql.Constants;
import com.distributed.airways.graphql.GraphQLQuery;
import com.distributed.airways.graphql.GraphQLResponseCities;
import com.distributed.airways.graphql.GraphQLResponseFlights;
import com.distributed.airways.model.Flight;
import com.google.common.collect.ImmutableMap;
import graphql.schema.DataFetcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class BrokerService {
    private final DiscoveryClient discoveryClient;
    private final Environment environment;
    private final RestTemplate restTemplate = new RestTemplate();

    public DataFetcher<List<String>> getSourceCitiesDataFetcher() {
        return dataFetchingEnvironment -> {
            return getCitiesFromAirlines(Constants.SOURCE_CITIES_QUERY, "sourceCities");
        };
    }

    public DataFetcher<List<String>> getDestinationCitiesDataFetcher() {
        return dataFetchingEnvironment -> {
            return getCitiesFromAirlines(Constants.DESTINATION_CITIES_QUERY, "destinationCities");
        };
    }

    @Value("${SERVICES_PORT:80}")
    private String servicesPort;

    public DataFetcher<List<String>> getDestinationCitiesDataFetcher() {
        return dataFetchingEnvironment -> {
            return getCitiesFromAirlines(Constants.DESTINATION_CITIES_QUERY, "destinationCities");
        };
    }


    private List<String> getCitiesFromAirlines(String query, String type) {
        GraphQLQuery body = new GraphQLQuery(query, null);
        HttpEntity<GraphQLQuery> request = new HttpEntity<>(body);
        List<String> cities = new ArrayList<>();

        // Retrieve cities from each individual airline service
        for (String url : getServices()) {
            GraphQLResponseCities response =
                    restTemplate.postForObject(url, request, GraphQLResponseCities.class);
            List<String> dataCities = response.getData().get(type);
            for (String city : dataCities) {
                if (!cities.contains(city)) {
                    cities.add(city);
                }
            }
        }
        return cities;
    }

    private List<Flight> getFlightsFromAirlines(
            String date, String sourceCity, String destinationCity) {
        Map<String, String> variables =
                ImmutableMap.of(
                        "date", date, "sourceCity", sourceCity, "destinationCity", destinationCity);
        GraphQLQuery body = new GraphQLQuery(Constants.FLIGHTS_QUERY, variables);
        HttpEntity<GraphQLQuery> request = new HttpEntity<>(body);
        List<Flight> flights = new ArrayList<>();
        for (String url : getServices()) {
            GraphQLResponse response =
                    restTemplate.postForObject(url, request, GraphQLResponse.class);
            assert response != null;
            List<Flight> dataFlights = response.getData().get("flights");
            flights.addAll(dataFlights);
        }
        return flights;
    }

    // helper method to preserve docker compose compatibility
    private List<String> getServices() {
        List<String> services = new ArrayList<>();
        List<String> activeProfiles = Arrays.asList(environment.getActiveProfiles());
        if (activeProfiles.contains("kubernetes")) {
            System.out.println(Arrays.toString(environment.getActiveProfiles()));
            for (String service : discoveryClient.getServices()) {
                if (service.endsWith("service")) {
                    services.add(String.format("http://%s:%s/graphql", service, servicesPort));
                }
            }
        } else {
            for (String service : Constants.AIRLINE_HOSTS) {
                services.add(String.format("http://%s/graphql", service));
            }
        }
        return services;
    }
}
