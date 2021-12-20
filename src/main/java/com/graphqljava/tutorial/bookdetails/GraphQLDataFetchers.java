package com.graphqljava.tutorial.bookdetails;

import com.google.common.collect.ImmutableMap;
import graphql.schema.DataFetcher;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class GraphQLDataFetchers {

    private static List<Map<String, String>> books =
            Arrays.asList(
                    ImmutableMap.of(
                            "id",
                            "book-1",
                            "name",
                            "Harry Potter and the Philosopher's Stone",
                            "pageCount",
                            "223",
                            "authorId",
                            "author-1"),
                    ImmutableMap.of(
                            "id",
                            "book-2",
                            "name",
                            "Moby Dick",
                            "pageCount",
                            "635",
                            "authorId",
                            "author-2"),
                    ImmutableMap.of(
                            "id",
                            "book-3",
                            "name",
                            "Interview with the vampire",
                            "pageCount",
                            "371",
                            "authorId",
                            "author-3"));

    private static List<Map<String, String>> authors =
            Arrays.asList(
                    ImmutableMap.of("id", "author-1", "firstName", "Joanne", "lastName", "Rowling"),
                    ImmutableMap.of("id", "author-5", "firstName", "Joanne", "lastName", "Rowling"),
                    ImmutableMap.of(
                            "id", "author-4", "firstName", "Joanne", "lastName", "Rowling123"),
                    ImmutableMap.of(
                            "id", "author-2", "firstName", "Herman", "lastName", "Melville"),
                    ImmutableMap.of("id", "author-3", "firstName", "Anne", "lastName", "Rice"));

    public DataFetcher<Map<String, String>> getBookByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            String bookId = dataFetchingEnvironment.getArgument("id");
            return books.stream()
                    .filter(book -> book.get("id").equals(bookId))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher<Map<String, String>> getAuthorByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            String authorId = dataFetchingEnvironment.getArgument("id");
            return authors.stream()
                    .filter(author -> author.get("id").equals(authorId))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher<List<Map<String, String>>> getAuthorByFirstNameDataFetcher() {
        return dataFetchingEnvironment -> {
            String firstName = dataFetchingEnvironment.getArgument("firstName");
            return authors.stream()
                    .filter(author -> author.get("firstName").equals(firstName))
                    .collect(Collectors.toList());
        };
    }

    public DataFetcher<List<Map<String, String>>> getAuthorByNameDataFetcher() {
        return dataFetchingEnvironment -> {
            String firstName = dataFetchingEnvironment.getArgument("firstName");
            String lastName = dataFetchingEnvironment.getArgument("lastName");
            return authors.stream()
                    .filter(
                            author ->
                                    author.get("firstName").equals(firstName)
                                            && author.get("lastName").equals(lastName))
                    .collect(Collectors.toList());
        };
    }

    public DataFetcher<Map<String, String>> getAuthorDataFetcher() {
        return dataFetchingEnvironment -> {
            Map<String, String> book = dataFetchingEnvironment.getSource();
            String authorId = book.get("authorId");
            return authors.stream()
                    .filter(author -> author.get("id").equals(authorId))
                    .findFirst()
                    .orElse(null);
        };
    }
}
