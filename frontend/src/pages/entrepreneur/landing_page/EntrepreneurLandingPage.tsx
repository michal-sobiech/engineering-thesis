import { Flex, Text } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { Auth } from "../../../auth-context/Auth";
import { useWithAuth } from "../../../hooks/useWithAuth";
import { EnterprisesScrollableList } from "./EntreprisesScrollableList";

export const EntrepreneurLandingPage = () => {
    const withAuth = useWithAuth();
    const [firstName, setFirstName] = useState<string>("");

    const setFirstNameUsingAuth = (auth: Auth) => setFirstName(auth.firstName);

    useEffect(() => withAuth(setFirstNameUsingAuth), []);

    return <Flex
        direction="column">
        <Text>
            Hello, {firstName}!
        </Text>
        <EnterprisesScrollableList />
    </Flex>
}