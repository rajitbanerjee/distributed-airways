package com.distributed.airways.graphql;

import com.distributed.airways.model.Flight;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GraphQLResponse {
    private Map<String, List<Flight>> data;
}
