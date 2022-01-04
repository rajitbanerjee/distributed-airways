export interface GraphQLResponse {
  sourceCities?: string[];
  destinationCities?: string[];
  flights?: Flight[];
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
  transitCity: string;
  transitAirport: string;
  dayOfWeek: string[];
  price: number[];
  category: string[];
}
