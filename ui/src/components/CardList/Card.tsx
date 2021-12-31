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
        <img src={logo} className="logo" />

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
  switch(category) {
    // American Airlines
    case "None": return "red";
    case "Silver": return "blue";
    case "Gold": return "yellow"; 
    case "Platinum": return "green"
    // Cathay Pacific and Emirates
    case "Economy": return "green";
    case "Business": return "blue";
    case "First": return "red";
    // Ryanair
    case "Value": return "green";
    case "Regular": return "blue";
    case "Plus": return "red";
    default: return "gray"
  }
}

const makePriceBadges = (price: number, category: string): JSX.Element => {
  return (
    <Badge colorScheme={badgeColorsMap(category)} fontSize="sm">
      {category}: {numberToEuro(price)}
    </Badge>
  );
};

const numberToEuro = (n: number) => new Intl.NumberFormat("en-IE", { style: "currency", currency: "EUR" }).format(n);

export default Card;
