import { Box, Center } from "@chakra-ui/react";
import LogInPanel from "./panel/LogInPanel";



export const LogInPage = () => {
    return <Center height="100vh">
        <Box width="40vw">
            <LogInPanel />
        </Box>
    </Center>;
};