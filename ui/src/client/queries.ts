import { gql } from "graphql-request";

export const flightsByDateSourceCityAndDestinationCity = gql`
  query Flights($date: String, $sourceCity: String, $destinationCity: String) {
    flights(date: $date, sourceCity: $sourceCity, destinationCity: $destinationCity) {
      id
      airline
      flightNumber
      time
      sourceCity
      destinationCity
      sourceAirport
      destinationAirport
      dayOfWeek
      price
      category
    }
  }
`;

export const sourceCities = gql`
  query SourceCities {
    sourceCities
  }
`;

export const destinationCities = gql`
  query DestinationCities {
    destinationCities
  }
`;
