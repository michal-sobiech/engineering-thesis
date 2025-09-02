import { Center, Flex } from "@chakra-ui/react";
import Message from "./Message";
import ProceedToLogInButton from "./ProceedToLogInButton";

const Panel = () => {
    return <Flex bg="white"
        p="5"
        rounded="lg"
        shadow="lg"
        direction="column"
        gap="10px">
        <Center>
            <Message />
        </Center>
        <ProceedToLogInButton />
    </Flex>;
};

export default Panel;