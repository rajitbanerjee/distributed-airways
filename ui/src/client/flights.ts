import { request } from "graphql-request";
import * as queries from "./queries";
import { GraphQLResponse } from "./types";

const BROKER_ENDPOINT = "http://localhost:8080/graphql";

const makeRequest = async (query: string, variables?: { [key: string]: any }): Promise<GraphQLResponse> => {
  return request(BROKER_ENDPOINT, query, variables);
};

export const getFlights = async (variables: {
  date: string;
  sourceCity: string;
  destinationCity: string;
}): Promise<GraphQLResponse> => {
  return makeRequest(queries.flightsByDateSourceCityAndDestinationCity, variables);
};

export const getSourceCityOptions = async (): Promise<GraphQLResponse> => {
  return makeRequest(queries.sourceCities);
};

export const getDestinationCityOptions = async (): Promise<GraphQLResponse> => {
  return makeRequest(queries.destinationCities);
};
