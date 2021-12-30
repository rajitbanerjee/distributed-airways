import { render, screen } from "@testing-library/react";
import App from ".";

test("renders without crashing", () => {
  render(<App />);
  const element = screen.getByText(/Distributed Airways/i);
  expect(element).toBeInTheDocument();
});
