import { Badge, Box, HStack, Text, VStack } from "@chakra-ui/react";
import { Flight } from "client/types";
import _ from "lodash";
import "./CardList.css";

interface Props {
  flight: Flight;
}

const Card = ({ flight }: Props): JSX.Element => {
  const airline = flight.airline;
  const logo = `/airlines/${airline}.png`;
  const source = `${flight.sourceCity} (${flight.sourceAirport})`;
  const destination = `${flight.destinationCity} (${flight.destinationAirport})`;
  const departureTime = flight.time[0];
  const arrivalTime = flight.time[flight.time.length - 1];
  const isDirect = flight.transitCity.length === 0;
  const transit = `${flight.transitCity} (${flight.transitAirport})`;
  const transitArrivalTime = flight.time[1];
  const transitDepartureTime = flight.time[2];

  return (
    <Box
      borderWidth="1px"
      borderColor="lightgrey.50"
      borderRadius="5px"
      boxShadow="md"
      padding="1em"
      _hover={{
        borderWidth: "1.5px",
        borderColor: "teal",
      }}
    >
      <HStack spacing={10}>
        <img src={logo} className="logo" alt="Airline logo" />

        <VStack spacing={-1}>
          <Text fontSize="large">{airline}</Text>
          {flight.flightNumber.map((n) => (
            <Text>{n}</Text>
          ))}
        </VStack>

        <VStack>
          <Text fontSize="x-large">{departureTime}</Text>
          <Text>{source}</Text>
        </VStack>

        {isDirect ? (
          <VStack>
            <Text color="green" fontSize="xs">
              Direct
            </Text>
          </VStack>
        ) : (
          <VStack spacing={-1}>
            <Text color="red" fontSize="xs">
              Transit
            </Text>
            <Text fontSize="xs">{transit}</Text>
            <Text fontSize="xs">A: {transitArrivalTime}</Text>
            <Text fontSize="xs">D: {transitDepartureTime}</Text>
          </VStack>
        )}

        <VStack>
          <Text fontSize="x-large">{arrivalTime}</Text>
          <Text>{destination}</Text>
        </VStack>

        <VStack>{_.zip(flight.price, flight.category).map((z) => makePriceBadges(z[0]!, z[1]!))}</VStack>
      </HStack>
    </Box>
  );
};

const badgeColorsMap = (category: string) => {
  if (["None", "First", "Plus"].includes(category)) return "red";
  if (["Silver", "Business", "Regular"].includes(category)) return "blue";
  if (["Platinum", "Economy", "Value"].includes(category)) return "green";
  if (["Gold"].includes(category)) return "yellow";
  return "gray";
};

const makePriceBadges = (price: number, category: string): JSX.Element => {
  return (
    <Badge colorScheme={badgeColorsMap(category)} fontSize="sm">
      {category}: {numberToEuro(price)}
    </Badge>
  );
};

const numberToEuro = (n: number) => new Intl.NumberFormat("en-IE", { style: "currency", currency: "EUR" }).format(n);

export default Card;
