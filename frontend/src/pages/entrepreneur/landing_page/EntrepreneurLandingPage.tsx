import { Center, Flex, Separator, Text } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { entrepreneursApi } from "../../../api/entrepreneurs-api";
import { StandardButton } from "../../../common/StandardButton";
import { StandardPanel } from "../../../common/StandardPanel";
import { routes } from "../../../router/routes";
import { EnterprisesScrollableList } from "./EntreprisesScrollableList";

export const EntrepreneurLandingPage = () => {
    const navigate = useNavigate();

    const [firstName, setFirstName] = useState<string>("");

    useEffect(() => {
        async function loadData() {
            const { firstName } = await entrepreneursApi.getMeEntrepreneur();
            setFirstName(firstName);
        }
        loadData();
    }, []);

    const onCreateEnterpriseClick = () => {
        navigate(routes.createEnterprise);
    }

    return <Center height="100vh">
        <StandardPanel>
            <Flex gap="10px"
                direction="column">
                <Text fontSize="xl">
                    Hello, {firstName}!
                </Text>
                <Separator orientation="horizontal" width="100%" borderColor="primary.lightGray" />
                <Text fontSize="xs">
                    Your enterprises
                </Text>
                <EnterprisesScrollableList />
                <StandardButton fontSize="xs" onClick={onCreateEnterpriseClick}>
                    Create an enterprise
                </StandardButton>
            </Flex>
        </StandardPanel>
    </Center>

}