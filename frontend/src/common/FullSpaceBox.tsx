import { Box, BoxProps } from "@chakra-ui/react"
import { FC } from "react"

export const FullSpaceBox: FC<BoxProps> = ({ children, ...props }) => {
    return <Box height="100%" width="100%" {...props}>
        {children}
    </Box>
}