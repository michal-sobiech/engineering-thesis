import { Box, Center } from "@chakra-ui/react";
import { SignUpEnterPasswordPanel } from "./SignUpEnterPasswordPanel";

const SignUpEnterPasswordPage = () => {
    return <Center height="100%">
        <Box width="40%">
            <SignUpEnterPasswordPanel />
        </Box>
    </Center >;
}

export default SignUpEnterPasswordPage;