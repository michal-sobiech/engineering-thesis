import { Box, Center } from "@chakra-ui/react";
import { SignUpEnterNamePanel } from "./panel/SignUpEnterNamePanel";

export const SignUpEnterNamePage = () => {
    return <Center height="100vh">
        <Box width="40vw">
            <SignUpEnterNamePanel />
        </Box>
    </Center>;
};