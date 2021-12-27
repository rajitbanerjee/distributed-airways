package com.distributed.airways.client;

import com.distributed.airways.graphql.Constants;
import com.distributed.airways.graphql.GraphQLQuery;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import java.util.Arrays;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

public class Client {

    private static final String HOST = "localhost:" + Constants.BROKER_PORT;
    private static final String[][] REQUESTS = {
        {"2021-12-26", "Dublin", "Kolkata"},
        {"2021-12-27", "Dublin", "London"}
    };

    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();
        for (String[] request : REQUESTS) {
            Map<String, String> variables =
                    ImmutableMap.of(
                            "date",
                            request[0],
                            "sourceCity",
                            request[1],
                            "destinationCity",
                            request[2]);
            GraphQLQuery body = new GraphQLQuery(Constants.FLIGHTS_QUERY, variables);
            HttpEntity<GraphQLQuery> entity = new HttpEntity<>(body);
            String url = "http://" + HOST + "/graphql";
            String response = restTemplate.postForObject(url, entity, String.class);
            display(request, response);
        }
    }

    private static void display(String[] request, String response) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJsonString = gson.toJson(JsonParser.parseString(response));

        System.out.println(Arrays.toString(request));
        System.out.println(prettyJsonString);
        System.out.println("\n");
    }
}
