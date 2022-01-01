package com.distributed.airways.service;

import com.distributed.airways.utils.FileIO;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@Component
@RequiredArgsConstructor
public class GraphQLProvider {
    private static final String SCHEMA = "graphql/schema.graphqls";
    private final BrokerService service;
    private GraphQL graphQL;

    @PostConstruct
    public void init() throws IOException {
        GraphQLSchema graphQLSchema = buildSchema(FileIO.readFileAsString(SCHEMA));
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() {
        Map<String, DataFetcher> dataFetchersMap = new HashMap<>();
        dataFetchersMap.put("sourceCities", service.getSourceCitiesDataFetcher());
        dataFetchersMap.put("destinationCities", service.getDestinationCitiesDataFetcher());
        dataFetchersMap.put("flights", service.getFlightsDataFetcher());
        return RuntimeWiring.newRuntimeWiring().type(newTypeWiring("Query").dataFetchers(dataFetchersMap)).build();
    }

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }
}
