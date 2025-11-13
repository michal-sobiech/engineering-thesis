import { Box, Center } from "@chakra-ui/react";
import { EmployeeCreationEnterNamePagePanel } from "./EmployeeCreationEnterNamePagePanel";

export const EmployeeCreationEnterNamePage = () => {
    return <Center height="100%">
        <Box width="40%">
            <EmployeeCreationEnterNamePagePanel />
        </Box>
    </Center>;
};