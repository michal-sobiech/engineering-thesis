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
import { errorErrResultAsyncFromPromise } from "../../../utils/result";
import { extractFileName } from "../../../utils/url";

export const EnterpriseStaffPage = () => {
    const navigate = useNavigate();
    const enterpriseId = useIntParam("enterpriseId");

    const [name, setName] = useState<string>("");
    const [description, setDescription] = useState<string>("");
    const [location, setLocation] = useState<string>("");
    const [logoFileName, setLogoFileName] = useState<string>("");
    const [logoFile, setLogoFile] = useState<File | null>(null);
    const [backgroundPhotoFileName, setBackgroundPhotoFileName] = useState<string>("");
    const [backgroundPhotoFile, setBackgroundPhotoFile] = useState<File | null>(null);

    const onDicardClick = () => {
        navigate(routes.enterprisePublic(enterpriseId));
    }

    const onSaveClick = () => {
        enterprisesApi.patchEnterprise(
            enterpriseId,
            name,
            description,
            location,
            logoFileName,
            logoFile ?? undefined,
            backgroundPhotoFileName,
            backgroundPhotoFile ?? undefined
        );
        navigate(routes.enterprisePublic(enterpriseId));
    }

    useEffect(() => {
        async function loadData(): Promise<void> {
            const promise = enterprisesApi.getEnterprise(enterpriseId);
            const result = await errorErrResultAsyncFromPromise(promise);
            if (result.isErr()) {
                navigate(routes.mainPage);
                return;
            }
            const data = result.value;

            setName(data.name);
            setDescription(data.description);
            setLocation(data.location);

            const logoFileName = extractFileName(data.logoUrl);
            if (logoFileName.isOk()) {
                setLogoFileName(logoFileName.value);
            }

            const backgroundPhotoFileName = extractFileName(data.backgroundPhotoUrl);
            if (backgroundPhotoFileName.isOk()) {
                setBackgroundPhotoFileName(backgroundPhotoFileName.value);
            }
        }
        loadData();
    }, []);

    return <Center height="100vh">
        <StandardPanel>
            <StandardFlex>
                <StandardLabeledContainer label="Name">
                    <StandardTextField text={name} setText={setName} placeholder="Name" />
                </StandardLabeledContainer>
                <StandardLabeledContainer label="Description">
                    <StandardTextField text={description} setText={setDescription} placeholder="Description" />
                </StandardLabeledContainer>
                <StandardLabeledContainer label="Location">
                    <StandardTextField text={location} setText={setLocation} placeholder="Location" />
                </StandardLabeledContainer>
                <StandardLabeledContainer label="Logo">
                    <StandardFileInput fileName={logoFileName} setFileName={setLogoFileName} setFile={setLogoFile} />
                </StandardLabeledContainer>
                <StandardLabeledContainer label="Background photo">
                    <StandardFileInput fileName={backgroundPhotoFileName} setFileName={setBackgroundPhotoFileName} setFile={setBackgroundPhotoFile} />
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