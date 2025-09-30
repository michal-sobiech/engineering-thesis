import { Flex, Text } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { entrepreneursApi } from "../../../api/entrepreneurs-api";
import { EnterprisesScrollableList } from "./EntreprisesScrollableList";

export const EntrepreneurLandingPage = () => {
    const [firstName, setFirstName] = useState<string>("");

    useEffect(() => {
        async function loadData() {
            const { firstName } = await entrepreneursApi.getMeEntrepreneur();
            setFirstName(firstName);
        }
        loadData();
    }, []);

    return <Flex
        direction="column">
        <Text>
            Hello, {firstName}!
        </Text>
        <EnterprisesScrollableList />
    </Flex>
}