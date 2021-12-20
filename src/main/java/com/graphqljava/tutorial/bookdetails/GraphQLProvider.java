package com.graphqljava.tutorial.bookdetails;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class GraphQLProvider {

    @Autowired GraphQLDataFetchers graphQLDataFetchers;

    private GraphQL graphQL;

    @PostConstruct
    public void init() throws IOException {
        URL url = Resources.getResource("schema.graphqls");
        String sdl = Resources.toString(url, Charsets.UTF_8);
        GraphQLSchema graphQLSchema = buildSchema(sdl);
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
        dataFetchersMap.put("bookById", graphQLDataFetchers.getBookByIdDataFetcher());
        dataFetchersMap.put("authorById", graphQLDataFetchers.getAuthorByIdDataFetcher());
        dataFetchersMap.put(
                "authorByFirstName", graphQLDataFetchers.getAuthorByFirstNameDataFetcher());
        dataFetchersMap.put("authorByName", graphQLDataFetchers.getAuthorByNameDataFetcher());

        return RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Query").dataFetchers(dataFetchersMap))
                .type(
                        newTypeWiring("Book")
                                .dataFetcher("author", graphQLDataFetchers.getAuthorDataFetcher()))
                .build();
    }

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }
}
