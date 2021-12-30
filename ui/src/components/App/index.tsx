import { Heading, VStack } from "@chakra-ui/react";
import { getFlights, getSourceCityOptions, getDestinationCityOptions } from "client/flights";
import { Flight } from "client/types";
import CardList from "components/CardList";
import SearchBar from "components/SearchBar";
import React, { useEffect, useState } from "react";

const dateToYMD = (date: Date): string => date.toISOString().split("T")[0];

const App: React.FC = (): JSX.Element => {
  const [date, setDate] = useState<Date | undefined>(undefined);
  const [sourceCity, setSourceCity] = useState<string | undefined>(undefined);
  const [destinationCity, setDestinationCity] = useState<string | undefined>(undefined);

  const [flights, setFlights] = useState<Flight[] | undefined>(undefined);
  const [sourceCityOptions, setSourceCityOptions] = useState<string[] | undefined>(undefined);
  const [destinationCityOptions, setDestinationCityOptions] = useState<string[] | undefined>(undefined);

  const [submitForm, setSubmitForm] = useState(false);
  const [isLoading, setLoading] = useState(false);
  const [clear, setClear] = useState(false);

  useEffect(() => {
    if (!sourceCityOptions || !destinationCityOptions) handleCities();
    if (submitForm && date && sourceCity && destinationCity) handleSubmit();
    if (clear) handleClear();
  }, [submitForm, clear]);

  const handleCities = () => {
    getSourceCityOptions().then((response) => setSourceCityOptions(response.cities));
    getDestinationCityOptions().then((response) => setDestinationCityOptions(response.cities));
  };

  const handleSubmit = () => {
    setLoading(true);
    getFlights({ date: dateToYMD(date!), sourceCity: sourceCity!, destinationCity: destinationCity! })
      .then((response) => setFlights(response.flights))
      .finally(() => {
        setLoading(false);
        setSubmitForm(false);
      });
  };

  const handleClear = () => {
    setDate(undefined);
    setSourceCity(undefined);
    setDestinationCity(undefined);
    setFlights(undefined);
    setClear(false);
  };

  return (
    <VStack color="black" bgColor="white" minH="100vh" textAlign="center" paddingTop="5em">
      <Heading fontSize="lg">Distributed Airways</Heading>
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
      <CardList flights={flights} />
    </VStack>
  );
};

export default App;
