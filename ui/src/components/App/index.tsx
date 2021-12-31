import { Heading, VStack } from "@chakra-ui/react";
import { getDestinationCityOptions, getFlights, getSourceCityOptions } from "client/flights";
import { Flight } from "client/types";
import CardList from "components/CardList";
import Footer from "components/Footer";
import SearchBar from "components/SearchBar";
import React, { useEffect, useState } from "react";

const App: React.FC = (): JSX.Element => {
  // Search parameters state
  const [date, setDate] = useState<Date | undefined>(undefined);
  const [sourceCity, setSourceCity] = useState<string | undefined>(undefined);
  const [destinationCity, setDestinationCity] = useState<string | undefined>(undefined);

  // Broker responses state
  const [sourceCityOptions, setSourceCityOptions] = useState<string[] | undefined>(undefined);
  const [destinationCityOptions, setDestinationCityOptions] = useState<string[] | undefined>(undefined);
  const [flights, setFlights] = useState<Flight[] | undefined>(undefined);

  // Buttons state
  const [submitForm, setSubmitForm] = useState(false);
  const [isLoading, setLoading] = useState(false);
  const [clear, setClear] = useState(false);

  useEffect(() => {
    // Store all possible source and destination cities
    if (!sourceCityOptions || !destinationCityOptions) {
      getSourceCityOptions().then((response) => setSourceCityOptions(response.sourceCities));
      getDestinationCityOptions().then((response) => setDestinationCityOptions(response.destinationCities));
    }

    // Show flights
    if (submitForm && date && sourceCity && destinationCity) {
      setLoading(true);
      getFlights({ date: dateToYMD(date), sourceCity, destinationCity })
        .then((response) => setFlights(response.flights))
        .finally(() => {
          setLoading(false);
          setSubmitForm(false);
        });
    }

    // Clear search
    if (clear) {
      setDate(undefined);
      setSourceCity(undefined);
      setDestinationCity(undefined);
      setFlights(undefined);
      setClear(false);
    }
  }, [sourceCityOptions, destinationCityOptions, submitForm, date, sourceCity, destinationCity, clear]);

  return (
    <VStack color="black" bgColor="white" minH="100vh" textAlign="center">
      <VStack paddingTop="10em" paddingBottom="10em" spacing={7}>
        <img src="/logo.png" alt="Team logo" width="100" box-shadow="50px" />

        <Heading fontSize="lg" color="#606060">
          Distributed Airways
        </Heading>

        <SearchBar
          date={date}
          setDate={setDate}
          sourceCity={sourceCity}
          setSourceCity={setSourceCity}
          destinationCity={destinationCity}
          setDestinationCity={setDestinationCity}
          sourceCityOptions={sourceCityOptions}
          destinationCityOptions={destinationCityOptions}
          setSubmitForm={setSubmitForm}
          isLoading={isLoading}
          setClear={setClear}
        />

        <CardList longDate={date ? dateToFull(date) : undefined} flights={flights} />
      </VStack>
      <Footer />
    </VStack>
  );
};

const dateToYMD = (date: Date): string => date.toISOString().split("T")[0];
const dateToFull = (date: Date): string => `Flights departing on ${date.toDateString()}`;

export default App;
