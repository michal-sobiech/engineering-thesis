import { Center } from "@chakra-ui/react";
import { useState } from "react";
import { useNavigate } from "react-router";
import { enterprisesApi } from "../../../api/enterprises-api";
import { StandardButton } from "../../../common/StandardButton";
import { StandardFileInput } from "../../../common/StandardFileInput";
import { StandardFlex } from "../../../common/StandardFlex";
import { StandardLabeledContainer } from "../../../common/StandardLabeledContainer";
import { StandardPanel } from "../../../common/StandardPanel";
import { StandardTextField } from "../../../common/StandardTextField";
import { routes } from "../../../router/routes";
import { errorErrResultAsyncFromPromise } from "../../../utils/result";
import { toastError } from "../../../utils/toast";

export const CreateEnterprisePage = () => {
    const navigate = useNavigate();

    const [name, setName] = useState<string>("");
    const [description, setDescription] = useState<string>("");
    const [location, setLocation] = useState<string>("");
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

        if (location === "") {
            toastError("Enter the location");
            return;
        }

        const promise = enterprisesApi.createEnterprise({
            name,
            description,
            location,
            logoFileName: logoFileName === "" ? undefined : logoFileName,
            logoFile: logoFile ?? undefined,
            backgroundPhotoFileName: backgroundPhotoFileName === "" ? undefined : backgroundPhotoFileName,
            backgroundPhotoFile: backgroundPhotoFile ?? undefined,
        });
        const result = await errorErrResultAsyncFromPromise(promise);

        if (result.isErr()) {
            toastError("Couldn't create enterprise. Try again later.");
            return;
        }

        const { enterpriseId } = result.value;
        const path = routes.enterprisePublic(enterpriseId);
        console.log(path);
        navigate(path);
    }

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
                <StandardLabeledContainer label="*Logo">
                    <StandardFileInput text={logoFileName} setText={setLogoFileName} setFile={setLogoFile} />
                </StandardLabeledContainer>
                <StandardLabeledContainer label="*Background photo">
                    <StandardFileInput text={backgroundPhotoFileName} setText={setBackgroundPhotoFileName} setFile={setBackgroundPhotoFile} />
                </StandardLabeledContainer>
                <StandardButton onClick={handleButtonClick}>Create enterprise</StandardButton>
            </StandardFlex>
        </StandardPanel>
    </Center>;
};