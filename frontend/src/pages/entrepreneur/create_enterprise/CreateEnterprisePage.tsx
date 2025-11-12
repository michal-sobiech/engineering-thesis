import { Box, Center } from "@chakra-ui/react";
import { useState } from "react";
import { useNavigate } from "react-router";
import { useEnterprisesApi } from "../../../api/enterprises-api";
import { MapLocationPicker } from "../../../common/MapLocationPicker";
import { StandardButton } from "../../../common/StandardButton";
import { StandardFileInput } from "../../../common/StandardFileInput";
import { StandardFlex } from "../../../common/StandardFlex";
import { StandardLabeledContainer } from "../../../common/StandardLabeledContainer";
import { StandardPanel } from "../../../common/StandardPanel";
import { StandardTextField } from "../../../common/StandardTextField";
import { Location } from "../../../GENERATED-api";
import { routes } from "../../../router/routes";
import { errorErrResultAsyncFromPromise } from "../../../utils/result";
import { toastError } from "../../../utils/toast";

export const CreateEnterprisePage = () => {
    const enterprisesApi = useEnterprisesApi();
    const navigate = useNavigate();

    const [name, setName] = useState<string>("");
    const [description, setDescription] = useState<string>("");
    const [address, setAddress] = useState<string | null>(null);
    const [position, setPosition] = useState<{ latitude: number; longitude: number } | null>(null);
    const [logoFileName, setLogoFileName] = useState<string>("");
    const [logoFile, setLogoFile] = useState<File | null>(null);
    const [backgroundPhotoFileName, setBackgroundPhotoFileName] = useState<string>("");
    const [backgroundPhotoFile, setBackgroundPhotoFile] = useState<File | null>(null);

    const handleButtonClick = async () => {
        if (name === "") {
            toastError("Choose an enterprise name!");
            return;
        }

        if (description === "") {
            toastError("Enter a description");
            return;
        }

        if (address === null || position === null) {
            toastError("Choose a location");
            return;
        }

        const location: Location = {
            address,
            longitude: position.longitude,
            latitude: position.latitude,
        };

        const promise = enterprisesApi.createEnterprise(
            name,
            description,
            address,
            position.longitude,
            position.latitude,
            logoFile ?? undefined,
            backgroundPhotoFile ?? undefined,
        );
        const result = await errorErrResultAsyncFromPromise(promise);

        if (result.isErr()) {
            toastError("Couldn't create enterprise. Try again later.");
            return;
        }

        const { enterpriseId } = result.value;
        const path = routes.enterprisePublic(enterpriseId);
        navigate(path);
    }

    return <Center height="100vh">
        <Box width="80vw" height="100%">
            <StandardPanel>
                <StandardFlex>
                    <StandardLabeledContainer label="Name">
                        <StandardTextField text={name} setText={setName} placeholder="Name" />
                    </StandardLabeledContainer>
                    <StandardLabeledContainer label="Description">
                        <StandardTextField text={description} setText={setDescription} placeholder="Description" />
                    </StandardLabeledContainer>
                    <StandardLabeledContainer label="Location">
                        <MapLocationPicker address={address} setAddress={setAddress} position={position} setPosition={setPosition} />
                    </StandardLabeledContainer>
                    <StandardLabeledContainer label="*Logo">
                        <StandardFileInput text={logoFileName} setText={setLogoFileName} setFile={setLogoFile} />
                    </StandardLabeledContainer>
                    <StandardLabeledContainer label="*Background photo">
                        <StandardFileInput text={backgroundPhotoFileName} setText={setBackgroundPhotoFileName} setFile={setBackgroundPhotoFile} />
                    </StandardLabeledContainer>
                    <StandardButton onClick={handleButtonClick}>Create enterprise</StandardButton>
                </StandardFlex>
            </StandardPanel>
        </Box>
    </Center>;
};