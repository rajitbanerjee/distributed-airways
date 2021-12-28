import { request } from "graphql-request";
import * as queries from "./queries";
import { GraphQLResponse } from "./types";

const ENDPOINT = "http://localhost:8080/graphql";

const getFlights = async (variables: {
  date: string;
  sourceCity: string;
  destinationCity: string;
}): Promise<GraphQLResponse | undefined> => {
  try {
    const response: GraphQLResponse = await request(
      ENDPOINT,
      queries.flightsByDateSourceCityAndDestinationCity,
      variables
    );
    return response;
  } catch (e) {
    console.log(e);
  }
};

// (async () => {
//   console.log(await getFlights("2021-12-30", "Dublin", "London"));
// })();
