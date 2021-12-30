import { Box } from "@chakra-ui/react";
import { Select } from "chakra-react-select";

interface Props {
  isSourceCity: boolean;
  cityOptions?: string[];
  city?: string;
  setCity: (city: string) => void;
}

interface SelectOption {
  label: string;
  value: string;
}

const makeSelectOption = (s: string) => ({ label: s, value: s });

const CityInput = ({ isSourceCity, cityOptions, city, setCity }: Props): JSX.Element => {
  const selectOptions: SelectOption[] | undefined = cityOptions?.map(makeSelectOption);

  return (
    <Box width="14em">
      <Select
        placeholder={isSourceCity ? "Source" : "Destination"}
        value={city ? makeSelectOption(city) : ""}
        options={selectOptions}
        onChange={(option: SelectOption) => setCity(option.value)}
      />
    </Box>
  );
};

export default CityInput;
