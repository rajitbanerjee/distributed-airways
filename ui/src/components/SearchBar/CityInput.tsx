import { Select } from "chakra-react-select";

interface Props {
  cities: string[];
  setCity: (city: string) => void;
}

interface SelectOption {
  label: string;
  value: string;
}

const CityInput = ({ cities, setCity }: Props): JSX.Element => {
  const selectOptions: SelectOption[] = cities.map((c) => ({ label: c, value: c }));
  return <Select options={selectOptions} onChange={(option: SelectOption) => setCity(option.value)} />;
};

export default CityInput;
