import { Box, BoxProps } from "@chakra-ui/react";
import { FC } from "react";

export const StandardPanel: FC<BoxProps> = ({ children, ...otherProps }) => {
    return (
        <Box bg="white"
            p="5"
            rounded="lg"
            shadow="lg"
            gap="10px"
            backgroundColor="primary.basicWhite"
            {...otherProps}>
            {children}
        </Box>
    );
}