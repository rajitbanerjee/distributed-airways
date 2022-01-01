package com.distributed.airways.service;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

import com.distributed.airways.repository.RyanAirRepository;
import com.distributed.airways.utils.FileIO;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class GraphQLProvider {

    @Autowired RyanAirService service;
    @Autowired RyanAirRepository flightRepository;

    private GraphQL graphQL;

    @PostConstruct
    public void init() throws IOException {
        GraphQLSchema graphQLSchema = buildSchema(FileIO.readFileAsString("schema.graphqls"));
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private GraphQLSchema buildSchema(String sdl) throws IOException {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() throws IOException {
        Map<String, DataFetcher> dataFetchersMap = new HashMap<>();
        dataFetchersMap.put("sourceCities", service.getSourceCitiesDataFetcher(flightRepository));
        dataFetchersMap.put(
                "destinationCities", service.getDestinationCitiesDataFetcher(flightRepository));
        dataFetchersMap.put("flights", service.getFlightsDataFetcher(flightRepository));
        return RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Query").dataFetchers(dataFetchersMap))
                .build();
    }

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }
}
