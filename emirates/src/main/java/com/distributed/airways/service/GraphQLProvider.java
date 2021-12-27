package com.distributed.airways.service;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

import com.distributed.airways.model.EmiratesFlight;
import com.distributed.airways.repository.FlightRepository;
import com.distributed.airways.utils.FileIO;
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
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class GraphQLProvider {

    @Autowired EmiratesService service;
    @Autowired FlightRepository flightRepository;

    private GraphQL graphQL;
    private List<EmiratesFlight> flights;
    private static final String SCHEMA = "graphql/schema.graphqls";
    private static final String JSON = "data/flights.json";

    @PostConstruct
    public void init() throws IOException {
        GraphQLSchema graphQLSchema = buildSchema(FileIO.readFileAsString(SCHEMA));
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
        populateRepository();
    }

    private void populateRepository() {
        try {
            TypeLiteral<List<EmiratesFlight>> type = new TypeLiteral<List<EmiratesFlight>>() {};
            flights = JsonIterator.deserialize(FileIO.readFileAsString(JSON), type);
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
        DataFetcher<List<EmiratesFlight>> fetcher = service.getFlightsDataFetcher(flightRepository);
        return RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Query").dataFetcher("flights", fetcher))
                .build();
    }

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }
}
