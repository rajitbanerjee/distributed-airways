import { Box, Text } from "@chakra-ui/react";

const Footer = (): JSX.Element => {
  return (
    <Box position="absolute" bottom={0} width="1.0" height="2.5rem">
      <Text>&copy; 2021 Distributed Airways, UCD COMP30220</Text>
    </Box>
  );
};

export default Footer;
