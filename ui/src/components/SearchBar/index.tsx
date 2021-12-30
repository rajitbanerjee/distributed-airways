import CustomDatePicker from "./CustomDatePicker";
import CityInput from "./CityInput";

interface Props {
  date: Date;
  setDate: (date: Date) => void;
  setSourceCity: (city: string) => void;
  setDestinationCity: (city: string) => void;
  sourceCityOptions: string[];
  destinationCityOptions: string[];
}

const SearchBar = ({
  date,
  setDate,
  setSourceCity,
  setDestinationCity,
  sourceCityOptions,
  destinationCityOptions,
}: Props): JSX.Element => {
  return (
    <div>
      <CustomDatePicker date={date} setDate={setDate}></CustomDatePicker>
      <CityInput cities={sourceCityOptions} setCity={setSourceCity} />
      <CityInput cities={destinationCityOptions} setCity={setDestinationCity} />
    </div>
  );
};

export default SearchBar;
