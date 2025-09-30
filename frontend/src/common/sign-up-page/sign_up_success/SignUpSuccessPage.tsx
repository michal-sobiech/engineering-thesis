import { Box, Center } from "@chakra-ui/react";
import { SignUpSuccessPanel } from "./SignUpSuccessPanel";

const SignUpSuccessPage = () => {
    return <Center width="100vw" height="100vh">
        <Box>
            <SignUpSuccessPanel />
        </Box>
    </Center>;
}

export default SignUpSuccessPage;