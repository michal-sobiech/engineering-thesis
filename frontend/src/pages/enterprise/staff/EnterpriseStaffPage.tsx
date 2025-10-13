import { Box, Center, Flex } from "@chakra-ui/react";
import { err, ok, Result, ResultAsync } from "neverthrow";
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
import { fetchPhoto } from "../../../utils/photo";
import { errorErrResultAsyncFromPromise, promiseResultToErrorAsyncResult } from "../../../utils/result";

export const EnterpriseStaffPage = () => {
    const navigate = useNavigate();
    const enterpriseId = useIntParam("enterpriseId");

    const [name, setName] = useState<string | null>(null);
    const [description, setDescription] = useState<string | null>(null);
    const [location, setLocation] = useState<string | null>(null);
    // const [initialLogoFileName, setInitialLogoFileName] = useState<string | null>(null);
    const [logoFile, setLogoFile] = useState<File | null>(null);
    // const [initialBackgroundPhotoFileName, setInitialBackgroundPhotoFileName] = useState<string | null>(null);
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

interface EnterpriseData {
    name: string;
    description: string;
    location: string;
    logo: File | null;
    backgroundPhoto: File | null;
}

function fetchEnterpriseData(enterpriseId: number): ResultAsync<EnterpriseData, Error> {

    async function createPromise(): Promise<Result<EnterpriseData, Error>> {
        const enterprisePromise = enterprisesApi.getEnterprise(enterpriseId);
        const enterpriseResult = await errorErrResultAsyncFromPromise(enterprisePromise);
        if (enterpriseResult.isErr()) {
            return err(enterpriseResult.error);
        }

        const logoPhotoId = enterpriseResult.value.logoPhotoId;
        const backgroundPhotoId = enterpriseResult.value.backgroundPhotoId;

        let logoFile: File | null;
        if (logoPhotoId === undefined) {
            logoFile = null;
        } else {
            const result = await fetchPhoto(logoPhotoId);
            if (result.isErr()) {
                return err(result.error);
            }
            logoFile = result.value;
        }

        let backgroundPhotoFile: File | null;
        if (backgroundPhotoId === undefined) {
            backgroundPhotoFile = null;
        } else {
            const result = await fetchPhoto(backgroundPhotoId);
            if (result.isErr()) {
                return err(result.error);
            }
            backgroundPhotoFile = result.value;
        }

        return ok<EnterpriseData>({
            name: enterpriseResult.value.name,
            description: enterpriseResult.value.description,
            location: enterpriseResult.value.location,
            logo: logoFile,
            backgroundPhoto: backgroundPhotoFile,
        });
    }

    return promiseResultToErrorAsyncResult(createPromise());

}
