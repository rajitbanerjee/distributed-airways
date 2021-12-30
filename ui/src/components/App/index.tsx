import { getFlights } from "client/flights";
import React, { useState } from "react";
import SearchBar from "components/SearchBar";
import "./App.css";

const dateToYMD = (date: Date): string => date.toISOString().split("T")[0];
const sourceCityOptions = ["Dublin"];
const destinationCityOptions = ["London", "Melbourne"];

const App: React.FC = (): JSX.Element => {
  const [date, setDate] = useState(new Date());
  const [sourceCity, setSourceCity] = useState("");
  const [destinationCity, setDestinationCity] = useState("");

  console.log(dateToYMD(date), sourceCity, destinationCity);

  return (
    <div className="App">
      <p>Distributed Airways</p>
      <SearchBar
        date={date}
        setDate={setDate}
        setSourceCity={setSourceCity}
        setDestinationCity={setDestinationCity}
        sourceCityOptions={sourceCityOptions}
        destinationCityOptions={destinationCityOptions}
      ></SearchBar>
    </div>
  );
};

export default App;
