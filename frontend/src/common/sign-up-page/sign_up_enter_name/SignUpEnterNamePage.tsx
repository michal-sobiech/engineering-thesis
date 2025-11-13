import { Box, Center } from "@chakra-ui/react";
import { SignUpEnterNamePanel } from "./panel/SignUpEnterNamePanel";

export const SignUpEnterNamePage = () => {
    return <Center height="100%">
        <Box width="40%">
            <SignUpEnterNamePanel />
        </Box>
    </Center>;
};