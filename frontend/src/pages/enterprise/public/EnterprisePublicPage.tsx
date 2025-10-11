import { Box, Center, Flex, Text } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { enterprisesApi } from "../../../api/enterprises-api";
import { ScrollableList } from "../../../common/scrollable-list/ScrollableList";
import { StandardBox } from "../../../common/StandardBox";
import { StandardHorizontalSeparator } from "../../../common/StandardHorizontalSeparator";
import { StandardPanel } from "../../../common/StandardPanel";
import { useIntParam } from "../../../hooks/useIntParam";
import { errorErrResultAsyncFromPromise } from "../../../utils/result";
import { EnterprisePublicPageHeader } from "./EnterprisePublicPageHeader";
import { EnterprisePublicPageServicesList } from "./EnterprisePublicPageServicesList";

export const EnterprisePublicPage = () => {
    const enterpriseId = useIntParam("enterpriseId");

    const [enterpriseName, setEnterpriseName] = useState<string>("");
    const [enterpriseDescription, setEnterpriseDescription] = useState<string>("");
    const [enterpriseLocation, setEnterpriseLocation] = useState<string>("");

    useEffect(() => {
        async function loadEnterpriseData(): Promise<void> {
            const promise = enterprisesApi.getEnterprise(enterpriseId);
            const result = await errorErrResultAsyncFromPromise(promise);
            if (result.isOk()) {
                setEnterpriseName(result.value.name);
                setEnterpriseDescription(result.value.description);
                setEnterpriseLocation(result.value.location);
            }
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
                    <Flex direction="column" align="stretch" gap="10px">
                        <EnterprisePublicPageHeader enterpriseName={enterpriseName} />
                        <StandardHorizontalSeparator />
                        <Text> {enterpriseDescription} </Text>
                        <StandardHorizontalSeparator />
                        <Text> {"\u{1F4CD}"} {enterpriseLocation} </Text>
                        <StandardHorizontalSeparator />
                        <Flex direction="column" gap="5px">
                            <Text>Services</Text>
                            <EnterprisePublicPageServicesList enterpriseId={enterpriseId} />
                        </Flex>
                        <StandardHorizontalSeparator />
                        <Flex direction="column" gap="5px">
                            <Text>Reviews</Text>
                            <StandardBox>
                                <ScrollableList>

                                </ScrollableList>
                            </StandardBox>
                        </Flex>
                    </Flex>
                </StandardPanel>
            </Center>
        </Box >
    </Center >;

}