export interface GraphQLResponse {
  flights?: Flight[];
  cities?: string[];
}

export interface Flight {
  id: string;
  airline: string;
  flightNumber: string[];
  time: string[];
  sourceCity: string;
  destinationCity: string;
  sourceAirport: string;
  destinationAirport: string;
  dayOfWeek: string[];
  price: number[];
  category: string[];
}
