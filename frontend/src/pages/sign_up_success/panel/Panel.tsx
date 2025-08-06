import { Flex } from "@chakra-ui/react";
import LogInButton from "./LogInButton";
import SuccessMessage from "./SuccessMessage";

const Panel = () => {
    return <Flex
        bg="white"
        p="5"
        rounded="lg"
        shadow="lg"
        direction="column"
        gap="10px">
        <SuccessMessage />
        <LogInButton />
    </Flex>
}

export default Panel;