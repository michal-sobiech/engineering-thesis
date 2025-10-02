import { Center } from "@chakra-ui/react";
import { StandardPanel } from "../../StandardPanel";
import SignUpSuccessButton from "./SignUpSuccessButton";
import Message from "./SignUpSuccessMessage";

export const SignUpSuccessPanel = () => {
    return <StandardPanel>
        <Center>
            <Message />
        </Center>
        <SignUpSuccessButton />
    </StandardPanel>;
};