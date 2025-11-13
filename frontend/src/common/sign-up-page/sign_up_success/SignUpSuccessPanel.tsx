import { Center } from "@chakra-ui/react";
import { StandardFlex } from "../../StandardFlex";
import { StandardPanel } from "../../StandardPanel";
import SignUpSuccessButton from "./SignUpSuccessButton";
import Message from "./SignUpSuccessMessage";

export const SignUpSuccessPanel = () => {
    return <StandardPanel width="30%">
        <StandardFlex>
            <Center>
                <Message />
            </Center>
            <SignUpSuccessButton />
        </StandardFlex>
    </StandardPanel>;
};