import { Box, Center } from "@chakra-ui/react";
import { VoidCallback } from "../../VoidCallback";
import { SignUpEnterEmailPanel } from "./panel/SignUpEnterEmailPanel";

export interface SignUpEnterEmailPageProps {
    email: string,
    setEmail: VoidCallback<string>
}

export const SignUpEnterEmailPage = () => {
    return <Center height="100%">
        <Box width="40%">
            <SignUpEnterEmailPanel />
        </Box>
    </Center>;
}