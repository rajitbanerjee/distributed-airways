import { request } from "graphql-request";
import * as queries from "./queries";
import { GraphQLResponse } from "./types";

const ENDPOINT = "http://localhost:8080/graphql";

const makeRequest = async (query: string, variables: { [key: string]: any }): Promise<GraphQLResponse | undefined> => {
  return request(ENDPOINT, query, variables);
};

export const getFlights = async (variables: {
  date: string;
  sourceCity: string;
  destinationCity: string;
}): Promise<GraphQLResponse | undefined> => {
  return makeRequest(queries.flightsByDateSourceCityAndDestinationCity, variables);
};

// (async () => {
//   console.log(await getFlights("2021-12-30", "Dublin", "London"));
// })();
