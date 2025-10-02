import { Box, BoxProps } from "@chakra-ui/react"
import { FC } from "react"

export const StandardBox: FC<BoxProps> = ({ children, ...otherProps }) => {
    return <Box
        border="1px solid"
        borderColor="primary.lightGray"
        borderRadius="md"
        padding="10px"
        {...otherProps}
    >
        {children}
    </Box>
}