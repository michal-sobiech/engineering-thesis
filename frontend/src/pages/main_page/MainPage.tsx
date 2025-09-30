import { Box, Flex } from "@chakra-ui/react";
import { MainPageCustomerPanel } from "./MainPageCustomerPanel";
import { MainPageSeparator } from "./MainPageSeparator";

export const MainPage = () => {
    return <Flex direction="horizontal">
        <Box flex={1}>
            <MainPageCustomerPanel />
        </Box>
        <MainPageSeparator />
        <Box flex={1}>
            <MainPageCustomerPanel />
        </Box>
    </Flex>
}