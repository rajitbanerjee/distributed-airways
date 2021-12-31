package com.distributed.airways.service;

import com.distributed.airways.graphql.Constants;
import com.distributed.airways.graphql.GraphQLQuery;
import com.distributed.airways.graphql.GraphQLResponseCities;
import com.distributed.airways.graphql.GraphQLResponseFlights;
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
    private RestTemplate restTemplate = new RestTemplate();

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

    public DataFetcher<List<Flight>> getFlightsDataFetcher() throws IOException {
        return dataFetchingEnvironment -> {
            String date = dataFetchingEnvironment.getArgument("date");
            String sourceCity = dataFetchingEnvironment.getArgument("sourceCity");
            String destinationCity = dataFetchingEnvironment.getArgument("destinationCity");
            return getFlightsFromAirlines(date, sourceCity, destinationCity);
        };
    }

    private List<String> getCitiesFromAirlines(String query, String type) {
        GraphQLQuery body = new GraphQLQuery(query, null);
        HttpEntity<GraphQLQuery> request = new HttpEntity<>(body);
        List<String> cities = new ArrayList<>();

        // Retrieve cities from each individual airline service
        for (String airlineHost : Constants.AIRLINE_HOSTS) {
            String url = "http://" + airlineHost + "/graphql";
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

        // Retrieve flights from each individual airline service
        for (String airlineHost : Constants.AIRLINE_HOSTS) {
            String url = "http://" + airlineHost + "/graphql";
            GraphQLResponseFlights response =
                    restTemplate.postForObject(url, request, GraphQLResponseFlights.class);
            List<Flight> dataFlights = response.getData().get("flights");
            if (!dataFlights.isEmpty()) {
                flights.addAll(dataFlights);
            }
        }
        return flights;
    }
}
