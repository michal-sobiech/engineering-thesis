import { Flex, Text } from "@chakra-ui/react";
import { useState } from "react";
import EmailField from "../../../common/EmailField";
import LogInButton from "./LogInButton";

const Panel = () => {
    const [email, setEmail] = useState<string>("");
    const [label, setLabel] = useState<string>("");

    return <Flex
        bg="white"
        p="5"
        rounded="lg"
        shadow="lg"
        direction="column"
        gap="10px">
        <Text textAlign="center">
            Step 1: enter your email address
        </Text>
        <EmailField text={email} setText={setEmail} label={label} />
        <LogInButton />
    </Flex>
}

export default Panel;