import { Center } from "@chakra-ui/react";
import { StanadardPanel } from "../../../common/StandardPanel";
import SignUpSuccessButton from "./SignUpSuccessButton";
import Message from "./SignUpSuccessMessage";

export const SignUpSuccessPanel = () => {
    return <StanadardPanel>
        <Center>
            <Message />
        </Center>
        <SignUpSuccessButton />
    </StanadardPanel>;
};