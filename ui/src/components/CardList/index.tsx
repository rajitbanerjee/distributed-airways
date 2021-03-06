import { Text, VStack } from "@chakra-ui/react";
import { Flight } from "client/types";
import Card from "./Card";

interface Props {
  longDate?: string;
  flights?: Flight[];
}

const CardList = ({ longDate, flights }: Props): JSX.Element => {
  // Order flights by price (ascending)
  return (
    <VStack>
      <Text color="grey" fontSize="14px">
        {flights && flights.length > 0 ? longDate : "Please try changing the search parameters!"}
      </Text>
      {flights ? sortByPrice(flights).map(makeCard) : null}
    </VStack>
  );
};

const sortByPrice = (flights: Flight[]): Flight[] => flights.sort((a, b) => a.price[0] - b.price[0]);
const makeCard = (flight: Flight): JSX.Element => <Card flight={flight} />;

export default CardList;
