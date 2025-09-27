import { Flex, Text } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { auth } from "../../../auth/AuthProvider";
import { EnterprisesScrollableList } from "./EntreprisesScrollableList";

export const EntrepreneurLandingPage = () => {
    const [firstName, setFirstName] = useState<string>("");

    useEffect(() => setFirstName(auth.value?.firstName || ""), []);

    return <Flex
        direction="column">
        <Text>
            Hello, {firstName}!
        </Text>
        <EnterprisesScrollableList />
    </Flex>
}