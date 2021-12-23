package com.distributed.airways.service;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

import com.distributed.airways.model.RyanAirFlight;
import com.distributed.airways.repository.FlightRepository;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.jsoniter.JsonIterator;
import com.jsoniter.spi.TypeLiteral;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class GraphQLProvider {

    @Autowired RyanAirService service;
    @Autowired FlightRepository flightRepository;

    private GraphQL graphQL;
    private List<RyanAirFlight> flights;
    private static final String SCHEMA = "schema.graphqls";
    private static final String JSON = "flights.json";

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
            URL url = Resources.getResource(JSON);
            String jsonString = Resources.toString(url, Charsets.UTF_8);
            flights =
                    JsonIterator.deserialize(
                            jsonString, new TypeLiteral<List<RyanAirFlight>>() {});
            flightRepository.deleteAll();
            flightRepository.saveAll(flights);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
