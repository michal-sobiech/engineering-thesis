import { Center, Flex } from "@chakra-ui/react";
import SignUpSuccessButton from "./SignUpSuccessButton";
import Message from "./SignUpSuccessMessage";

export const SignUpSuccessPanel = () => {
    return <Flex bg="white"
        p="5"
        rounded="lg"
        shadow="lg"
        direction="column"
        gap="10px">
        <Center>
            <Message />
        </Center>
        <SignUpSuccessButton />
    </Flex>;
};