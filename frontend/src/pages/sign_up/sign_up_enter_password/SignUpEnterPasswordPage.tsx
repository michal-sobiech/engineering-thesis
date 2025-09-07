import { Box, Center } from "@chakra-ui/react";
import { SignUpEnterPasswordPanel } from "./SignUpEnterPasswordPanel";

const SignUpEnterPasswordPage = () => {
    return <Center height="100vh">
        <Box width="40vw">
            <SignUpEnterPasswordPanel />
        </Box>
    </Center >;
}

export default SignUpEnterPasswordPage;