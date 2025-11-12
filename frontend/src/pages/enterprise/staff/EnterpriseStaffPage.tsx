import { Box, Center, Flex, Heading } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { useEnterprisesApi } from "../../../api/enterprises-api";
import { LinkScrollableList } from "../../../common/LinkScrollableList";
import { StandardBox } from "../../../common/StandardBox";
import { StandardButton } from "../../../common/StandardButton";
import { StandardFileInput } from "../../../common/StandardFileInput";
import { StandardFlex } from "../../../common/StandardFlex";
import { StandardLabeledContainer } from "../../../common/StandardLabeledContainer";
import { StandardPanel } from "../../../common/StandardPanel";
import { StandardTextField } from "../../../common/StandardTextField";
import { StandardVerticalSeparator } from "../../../common/StandardVerticalSeparator";
import { Location } from "../../../GENERATED-api";
import { GetEnterpriseServicesResponseItem } from "../../../GENERATED-api/models/GetEnterpriseServicesResponseItem";
import { useIntParam } from "../../../hooks/useIntParam";
import { routes } from "../../../router/routes";
import { fetchServices } from "../../service/service-utils";
import { fetchEnterpriseData } from "../utils";

export const EnterpriseStaffPage = () => {
    const enterprisesApi = useEnterprisesApi();
    const navigate = useNavigate();
    const enterpriseId = useIntParam("enterpriseId");

    const [name, setName] = useState<string | null>(null);
    const [description, setDescription] = useState<string | null>(null);
    const [location, setLocation] = useState<Location | null>(null);
    const [logoFile, setLogoFile] = useState<File | null>(null);
    const [backgroundPhotoFile, setBackgroundPhotoFile] = useState<File | null>(null);

    const [services, setServices] = useState<GetEnterpriseServicesResponseItem[]>([]);

    const onDicardClick = () => {
        navigate(routes.enterprisePublic(enterpriseId));
    }

    const onSaveClick = () => {
        // enterprisesApi.patchEnterprise(
        //     enterpriseId,
        //     name ?? undefined,
        //     description ?? undefined,
        //     location ?? undefined,
        //     logoFile ?? undefined,
        //     backgroundPhotoFile ?? undefined
        // );
        // TODO
        navigate(routes.enterprisePublic(enterpriseId));
    }

    useEffect(() => {
        async function loadEnterpriseData(): Promise<void> {
            const data = await fetchEnterpriseData(enterpriseId, enterprisesApi);
            if (data.isErr()) {
                navigate(routes.mainPage);
                return;
            }
            setName(data.value.name);
            setDescription(data.value.description);
            setLocation(data.value.location);
            setLogoFile(data.value.logo);
            setBackgroundPhotoFile(data.value.backgroundPhoto);
        }
        loadEnterpriseData();
    }, []);

    useEffect(() => {
        async function loadServicesData(): Promise<void> {
            const result = await fetchServices(enterpriseId, enterprisesApi);
            if (result.isErr()) {
                navigate(routes.mainPage);
                return;
            }
            setServices(result.value);
        }
        loadServicesData();
    }, [])

    return <Box height="100vh" overflowY="auto" alignContent="center">
        <Center>
            <StandardPanel width="50%" height="80%">
                <StandardFlex>
                    <Heading size="xl" marginBottom={0}>
                        General settings
                    </Heading>
                    <StandardLabeledContainer label="Name">
                        <StandardTextField text={name ?? ""} setText={setName} placeholder="Name" />
                    </StandardLabeledContainer>
                    <StandardLabeledContainer label="Description">
                        <StandardTextField text={description ?? ""} setText={setDescription} placeholder="Description" />
                    </StandardLabeledContainer>
                    {
                        // TODO location
                    /* <StandardLabeledContainer label="Location">
                        <StandardTextField text={location ?? ""} setText={setLocation} placeholder="Location" />
                    </StandardLabeledContainer> */}
                    <StandardLabeledContainer label="*Logo">
                        <StandardFileInput
                            text={logoFile?.name ?? ""}
                            setText={() => { }}
                            setFile={setLogoFile}
                        />
                    </StandardLabeledContainer>
                    <StandardLabeledContainer label="*Background photo">
                        <StandardFileInput
                            text={backgroundPhotoFile?.name ?? ""}
                            setText={() => { }}
                            setFile={setBackgroundPhotoFile}
                        />
                    </StandardLabeledContainer>
                    <Box minHeight="5px" />
                    <Flex direction="row" gap="5px">
                        <StandardButton onClick={onDicardClick} flex="1" backgroundColor="primary.darkRed">
                            Discard
                        </StandardButton>
                        <StandardButton onClick={onSaveClick} flex="1" backgroundColor="primary.lightGreen">
                            Save
                        </StandardButton>
                    </Flex>

                    <StandardVerticalSeparator />

                    <Heading size="xl" marginBottom={0}>
                        Serivce settings
                    </Heading>
                    <StandardButton onClick={() => navigate(routes.enterpriseCreateService(enterpriseId))}>
                        Create a service
                    </StandardButton>
                    <StandardBox>
                        <LinkScrollableList items={services.map(service => ({
                            label: service.name,
                            url: routes.enterpriseCreateService(enterpriseId),
                        }))} />
                    </StandardBox>

                </StandardFlex>
            </StandardPanel>
        </Center >
    </Box>;
}