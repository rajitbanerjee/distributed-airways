import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import "./SearchBar.css";

interface Props {
  date?: Date;
  setDate: (date: Date) => void;
}

// https://reactdatepicker.com
const CustomDatePicker = ({ date, setDate }: Props): JSX.Element => {
  return (
    <div>
      <DatePicker
        placeholderText="Date"
        dateFormat="yyyy-MM-dd"
        selected={date}
        onChange={(date: Date) => setDate(date)}
        minDate={new Date()}
        monthsShown={2}
        closeOnScroll={true}
        className="date-field"
      />
    </div>
  );
};

export default CustomDatePicker;
