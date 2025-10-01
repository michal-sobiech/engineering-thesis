import { Box, Center, Flex, Separator, Text } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { entrepreneursApi } from "../../../api/entrepreneurs-api";
import { StandardButton } from "../../../common/StandardButton";
import { StanadardPanel } from "../../../common/StandardPanel";
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
        <StanadardPanel>
            <Flex gap="10px"
                direction="column">
                <Text fontSize="xl">
                    Hello, {firstName}!
                </Text>
                <Separator orientation="horizontal" width="100%" borderColor="primary.lightGray" />
                <Text fontSize="xs">
                    Your enterprises
                </Text>
                <Box
                    border="1px solid"
                    borderColor="primary.lightGray"
                    borderRadius="md"
                    padding="10px"
                >
                    <EnterprisesScrollableList />
                </Box>
                <StandardButton fontSize="xs" onClick={onCreateEnterpriseClick}>
                    Create an enterprise
                </StandardButton>
            </Flex>
        </StanadardPanel>
    </Center>

}