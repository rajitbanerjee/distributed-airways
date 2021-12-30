import { SearchIcon, SmallCloseIcon } from "@chakra-ui/icons";
import { Button, HStack } from "@chakra-ui/react";
import CityInput from "./CityInput";
import CustomDatePicker from "./CustomDatePicker";

interface Props {
  date?: Date;
  setDate: (date: Date) => void;
  sourceCity?: string;
  setSourceCity: (city: string) => void;
  destinationCity?: string;
  setDestinationCity: (city: string) => void;
  sourceCityOptions?: string[];
  destinationCityOptions?: string[];
  setSubmitForm: (submit: boolean) => void;
  isLoading: boolean;
  setClear: (clear: boolean) => void;
}

const SearchBar = ({
  date,
  setDate,
  sourceCity,
  setSourceCity,
  destinationCity,
  setDestinationCity,
  sourceCityOptions,
  destinationCityOptions,
  setSubmitForm,
  isLoading,
  setClear,
}: Props): JSX.Element => {
  return (
    <HStack flexWrap="wrap">
      <CustomDatePicker date={date} setDate={setDate}></CustomDatePicker>
      <CityInput isSourceCity={true} cityOptions={sourceCityOptions} city={sourceCity} setCity={setSourceCity} />
      <CityInput
        isSourceCity={false}
        cityOptions={destinationCityOptions}
        city={destinationCity}
        setCity={setDestinationCity}
      />
      <Button
        isLoading={isLoading}
        isDisabled={!date || !sourceCity || !destinationCity}
        variant="solid"
        rightIcon={<SearchIcon />}
        onClick={() => setSubmitForm(true)}
      >
        Search
      </Button>
      <Button
        isDisabled={!date && !sourceCity && !destinationCity}
        variant="solid"
        rightIcon={<SmallCloseIcon />}
        onClick={() => setClear(true)}
      >
        Clear
      </Button>
    </HStack>
  );
};

export default SearchBar;
