import { Box, Center, Flex } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { enterprisesApi } from "../../../api/enterprises-api";
import { StandardButton } from "../../../common/StandardButton";
import { StandardFileInput } from "../../../common/StandardFileInput";
import { StandardFlex } from "../../../common/StandardFlex";
import { StandardLabeledContainer } from "../../../common/StandardLabeledContainer";
import { StandardPanel } from "../../../common/StandardPanel";
import { StandardTextField } from "../../../common/StandardTextField";
import { StandardVerticalSeparator } from "../../../common/StandardVerticalSeparator";
import { useIntParam } from "../../../hooks/useIntParam";
import { routes } from "../../../router/routes";
import { fetchEnterpriseData } from "../utils";

export const EnterpriseStaffPage = () => {
    const navigate = useNavigate();
    const enterpriseId = useIntParam("enterpriseId");

    const [name, setName] = useState<string | null>(null);
    const [description, setDescription] = useState<string | null>(null);
    const [location, setLocation] = useState<string | null>(null);
    const [logoFile, setLogoFile] = useState<File | null>(null);
    const [backgroundPhotoFile, setBackgroundPhotoFile] = useState<File | null>(null);

    const onDicardClick = () => {
        navigate(routes.enterprisePublic(enterpriseId));
    }

    const onSaveClick = () => {
        enterprisesApi.patchEnterprise(
            enterpriseId,
            name ?? undefined,
            description ?? undefined,
            location ?? undefined,
            logoFile ?? undefined,
            backgroundPhotoFile ?? undefined
        );
        navigate(routes.enterprisePublic(enterpriseId));
    }

    useEffect(() => {
        async function loadData(): Promise<void> {
            const data = await fetchEnterpriseData(enterpriseId);
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

    return <Center height="100vh">
        <StandardPanel>
            <StandardFlex>
                <StandardLabeledContainer label="Name">
                    <StandardTextField text={name ?? ""} setText={setName} placeholder="Name" />
                </StandardLabeledContainer>
                <StandardLabeledContainer label="Description">
                    <StandardTextField text={description ?? ""} setText={setDescription} placeholder="Description" />
                </StandardLabeledContainer>
                <StandardLabeledContainer label="Location">
                    <StandardTextField text={location ?? ""} setText={setLocation} placeholder="Location" />
                </StandardLabeledContainer>
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
                <StandardVerticalSeparator />
                <Box minHeight="5px" />
                <Flex direction="row" gap="5px">
                    <StandardButton onClick={onDicardClick} flex="1" backgroundColor="primary.darkRed">
                        Discard
                    </StandardButton>
                    <StandardButton onClick={onSaveClick} flex="1" backgroundColor="primary.lightGreen">
                        Save
                    </StandardButton>
                </Flex>
            </StandardFlex>
        </StandardPanel>
    </Center >;
}