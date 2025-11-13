import { Box, Center, Flex, Text } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { useEnterprisesApi } from "../../../api/enterprises-api";
import { ScrollableList } from "../../../common/ScrollableList";
import { StandardBox } from "../../../common/StandardBox";
import { StandardHorizontalSeparator } from "../../../common/StandardHorizontalSeparator";
import { StandardLabeledContainer } from "../../../common/StandardLabeledContainer";
import { StandardPanel } from "../../../common/StandardPanel";
import { Location } from "../../../GENERATED-api";
import { useIntParam } from "../../../hooks/useIntParam";
import { routes } from "../../../router/routes";
import { fetchEnterpriseData } from "../utils";
import { EnterprisePublicPageHeader } from "./EnterprisePublicPageHeader";
import { EnterprisePublicPageServicesList } from "./EnterprisePublicPageServicesList";

export const EnterprisePublicPage = () => {
    const enteprisesApi = useEnterprisesApi();
    const navigate = useNavigate();
    const enterpriseId = useIntParam("enterpriseId");

    const [name, setName] = useState<string | null>(null);
    const [description, setDescription] = useState<string | null>(null);
    const [location, setLocation] = useState<Location | null>(null);
    const [logoFile, setLogoFile] = useState<File | null>(null);
    const [backgroundPhotoFile, setBackgroundPhotoFile] = useState<File | null>(null);

    useEffect(() => {
        async function loadData(): Promise<void> {
            const data = await fetchEnterpriseData(enterpriseId, enteprisesApi);
            if (data.isErr()) {
                throw navigate(routes.mainPage);
            }
            setName(data.value.name);
            setDescription(data.value.description);
            setLocation(data.value.location);
            setLogoFile(data.value.logo);
            setBackgroundPhotoFile(data.value.backgroundPhoto);
        }
        loadData();
    }, []);

    const logoObjectUrl = logoFile ? URL.createObjectURL(logoFile) : undefined;
    const backgroundPhotoObjectUrl = backgroundPhotoFile ? URL.createObjectURL(backgroundPhotoFile) : undefined;

    return <Center>
        <Box
            width="100%"
            height="100%"
            backgroundImage={`url(${logoObjectUrl})`}
            backgroundRepeat="no-repeat"
            backgroundPosition="center"
            backgroundSize="cover"
        >
            <Center width="100%" height="100%">
                <StandardPanel width="80%" height="90vh" padding="20px" backgroundColor="primary.basicWhite" >
                    <Flex direction="column" align="stretch" gap="10px">
                        <EnterprisePublicPageHeader enterpriseName={name ?? ""} imageUrl={backgroundPhotoObjectUrl} />
                        <StandardHorizontalSeparator />
                        <Text> {description ?? ""} </Text>
                        <StandardHorizontalSeparator />
                        <Text> {"\u{1F4CD}"} {location?.address} </Text>
                        <StandardHorizontalSeparator />
                        <StandardLabeledContainer label="Services">
                            <EnterprisePublicPageServicesList enterpriseId={enterpriseId} />
                        </StandardLabeledContainer>
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