import { request } from "graphql-request";
import * as queries from "./queries";
import { GraphQLResponse } from "./types";

const BROKER_PORT = process.env.REACT_APP_BROKER_PORT || 80;
const BROKER_HOST = process.env.REACT_APP_BROKER_HOST || 'localhost';
let path = '';
if (process.env.REACT_APP_ENVIRONMENT !== 'docker-compose') {
  path = 'api/';
}

const BROKER_ENDPOINT = `http://${BROKER_HOST}:${BROKER_PORT}/${path}graphql`;

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
