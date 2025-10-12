import { Box, Center, Flex } from "@chakra-ui/react";
import { ok } from "neverthrow";
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

    const [name, setName] = useState<string | null>(null);
    const [description, setDescription] = useState<string | null>(null);
    const [location, setLocation] = useState<string | null>(null);
    const [logoFileName, setLogoFileName] = useState<string | null>(null);
    const [logoFile, setLogoFile] = useState<File | null>(null);
    const [backgroundPhotoFileName, setBackgroundPhotoFileName] = useState<string | null>(null);
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
            logoFileName ?? undefined,
            logoFile ?? undefined,
            backgroundPhotoFileName ?? undefined,
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

            const logoFileName = data.logoUrl !== undefined
                ? extractFileName(data.logoUrl)
                : ok(null);
            if (logoFileName.isOk()) {
                setLogoFileName(logoFileName.value);
            }

            const backgroundPhotoFileName = data.backgroundPhotoUrl !== undefined
                ? extractFileName(data.backgroundPhotoUrl)
                : ok(null);
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
                    <StandardTextField text={name ?? ""} setText={setName} placeholder="Name" />
                </StandardLabeledContainer>
                <StandardLabeledContainer label="Description">
                    <StandardTextField text={description ?? ""} setText={setDescription} placeholder="Description" />
                </StandardLabeledContainer>
                <StandardLabeledContainer label="Location">
                    <StandardTextField text={location ?? ""} setText={setLocation} placeholder="Location" />
                </StandardLabeledContainer>
                <StandardLabeledContainer label="*Logo">
                    <StandardFileInput text={logoFileName ?? ""} setText={setLogoFileName} setFile={setLogoFile} />
                </StandardLabeledContainer>
                <StandardLabeledContainer label="*Background photo">
                    <StandardFileInput text={backgroundPhotoFileName ?? ""} setText={setBackgroundPhotoFileName} setFile={setBackgroundPhotoFile} />
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