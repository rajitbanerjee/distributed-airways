import { Text, VStack } from "@chakra-ui/react";
import { Flight } from "client/types";
import Card from "./Card";

interface Props {
  longDate?: string;
  flights?: Flight[];
}

const CardList = ({ longDate, flights }: Props): JSX.Element => {
  // Order flights by ascending order of price
  return (
    <VStack>
      <Text color="grey" fontSize="14px">
      {flights && flights.length > 0 ? longDate : "Please try a different search!"}
      </Text>
      {flights ? flights.sort((a, b) => a.price[0] - b.price[0]).map(makeCard) : null}
    </VStack>
  );
};

const makeCard = (flight: Flight): JSX.Element => {
  return <Card flight={flight} />;
};

export default CardList;
