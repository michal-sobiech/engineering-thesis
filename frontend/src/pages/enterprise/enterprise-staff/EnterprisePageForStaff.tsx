import { Box, Center, Flex, Text } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { enterprisesApi } from "../../../api/enterprises-api";
import { StandardPanel } from "../../../common/StandardPanel";
import { useIntParam } from "../../../hooks/useIntParam";
import { routes } from "../../../router/routes";
import { errorErrResultAsyncFromPromise } from "../../../utils/result";

export const EnterprisePageForStaff = () => {
    const navigate = useNavigate();
    const enterpriseId = useIntParam("enterpriseId");

    const [enterpriseName, setEnterpriseName] = useState<string>("");
    const [enterpriseDescription, setEnterpriseDescription] = useState<string>("");
    const [enterpriseLocation, setEnterpriseLocation] = useState<string>("");

    useEffect(() => {
        async function loadEnterpriseData(): Promise<void> {
            const promise = enterprisesApi.getEnterprise(enterpriseId);
            const result = await errorErrResultAsyncFromPromise(promise);
            if (result.isErr()) {
                navigate(routes.mainPage)
                return;
            }

            setEnterpriseName(result.value.enterpriseName);
            setEnterpriseDescription(result.value.description);
            setEnterpriseLocation(result.value.location);
        }

        loadEnterpriseData();
    }, []);

    return <Center>
        <Box
            width="100vw"
            height="100vh"
            backgroundImage="url(https://picsum.photos/1600/902)"
            backgroundRepeat="no-repeat"
            backgroundPosition="center"
            backgroundSize="cover"
        >
            <Center width="100%" height="100%">
                <StandardPanel width="80%" height="90vh" padding="20px" backgroundColor="primary.basicWhite" >
                    <Flex direction="column">
                        <Text fontSize="2xl">aaaaaaaaa</Text>
                    </Flex>
                </StandardPanel>
            </Center>
        </Box>
    </Center>;

}   