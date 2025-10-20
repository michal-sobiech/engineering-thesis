import { Box, BoxProps } from "@chakra-ui/react";
import { FC } from "react";

export const StandardConcaveBox: FC<BoxProps> = ({ children, ...otherProps }) => {
    const boxShadow = "inset 0 0 4px rgba(0, 0, 0, 0.15), inset 0 0 2px rgba(255,  255, 255, 0.75)";

    return <Box
        boxShadow={boxShadow}
        padding={0}
        borderRadius="md"
        {...otherProps}>
        {children}
    </Box>;
}