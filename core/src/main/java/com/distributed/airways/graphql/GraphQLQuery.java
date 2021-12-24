package com.distributed.airways.graphql;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GraphQLQuery {
    private String query;
    private Map<String, String> variables;
}
