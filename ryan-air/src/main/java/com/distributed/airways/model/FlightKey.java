package com.distributed.airways.model;

import static org.springframework.data.cassandra.core.cql.Ordering.DESCENDING;
import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.PARTITIONED;

import java.io.Serializable;
import java.util.List;
import lombok.*;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
@Data
@AllArgsConstructor
public class FlightKey implements Serializable {

    @PrimaryKeyColumn(name = "sourceCity", type = PARTITIONED)
    @Getter
    @Setter
    private String sourceCity;

    @PrimaryKeyColumn(name = "destinationCity", ordinal = 0)
    @Getter
    @Setter
    private String destinationCity;

    @PrimaryKeyColumn(name = "dayOfWeek", ordinal = 1, ordering = DESCENDING)
    @Getter
    @Setter
    private List<String> dayOfWeek;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FlightKey flightKey = (FlightKey) o;

        if (sourceCity != null
                ? !sourceCity.equals(flightKey.sourceCity)
                : flightKey.sourceCity != null) return false;
        if (destinationCity != null
                ? !destinationCity.equals(flightKey.destinationCity)
                : flightKey.destinationCity != null) return false;
        return dayOfWeek != null
                ? dayOfWeek.equals(flightKey.dayOfWeek)
                : flightKey.dayOfWeek == null;
    }

    @Override
    public int hashCode() {
        int result = sourceCity != null ? sourceCity.hashCode() : 0;
        result = 31 * result + (destinationCity != null ? destinationCity.hashCode() : 0);
        result = 31 * result + (dayOfWeek != null ? dayOfWeek.hashCode() : 0);
        return result;
    }
}
