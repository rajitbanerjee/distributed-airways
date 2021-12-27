package com.distributed.airways.service;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

import com.distributed.airways.model.RyanAirFlight;
import com.distributed.airways.repository.RyanAirRepository;
import com.distributed.airways.utils.FileIO;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class GraphQLProvider {

    @Autowired RyanAirService service;
    @Autowired RyanAirRepository flightRepository;

    private GraphQL graphQL;
    private List<RyanAirFlight> flights;
    private static final String SCHEMA = "schema.graphqls";
    private static final String JSON = "flights.json";
    private static final String BASE_PATH = "data";
    private final Gson gson = new Gson();;

    @PostConstruct
    public void init() throws IOException {
        URL url = Resources.getResource(SCHEMA);
        String sdl = Resources.toString(url, Charsets.UTF_8);
        GraphQLSchema graphQLSchema = buildSchema(sdl);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
        populateRepository();
    }

    private void populateRepository() {
        try {
            String jsonString = FileIO.readFileAsString(BASE_PATH + "/flights.json");
            Type listType = new TypeToken<List<RyanAirFlight>>() {}.getType();
            flights = gson.fromJson(jsonString, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        flightRepository.deleteAll();
        flightRepository.saveAll(flights);
        // try {
        //     String jsonString = FileIO.readFileAsString("flight.json");
        //     Type listType = new TypeToken<List<RyanAirFlight>>() {}.getType();
        //     flights = gson.fromJson(jsonString, listType);
        //     flightRepository.deleteAll();
        //     flightRepository.saveAll(flights);
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
    }

    private GraphQLSchema buildSchema(String sdl) throws IOException {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() throws IOException {
        DataFetcher<List<RyanAirFlight>> fetcher = service.getFlightsDataFetcher(flightRepository);
        return RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Query").dataFetcher("flights", fetcher))
                .build();
    }

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }
}
