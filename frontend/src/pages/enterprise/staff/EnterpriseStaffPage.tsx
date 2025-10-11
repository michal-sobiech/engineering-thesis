import { Box, Center, Flex } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { StandardButton } from "../../../common/StandardButton";
import { StandardFileInput } from "../../../common/StandardFileInput";
import { StandardFlex } from "../../../common/StandardFlex";
import { StandardLabeledContainer } from "../../../common/StandardLabeledContainer";
import { StandardPanel } from "../../../common/StandardPanel";
import { StandardTextField } from "../../../common/StandardTextField";
import { StandardVerticalSeparator } from "../../../common/StandardVerticalSeparator";
import { useIntParam } from "../../../hooks/useIntParam";
import { routes } from "../../../router/routes";

export const EnterpriseStaffPage = () => {
    const navigate = useNavigate();
    const enterpriseId = useIntParam("enterpriseId");

    const [name, setName] = useState<string>("");
    const [description, setDescription] = useState<string>("");
    const [location, setLocation] = useState<string>("");
    const [logoFileName, setLogoFileName] = useState<string>("");
    const [backgroundPhotoFileName, setBackgroundPhotoFileName] = useState<string>("");

    const onDicardClick = () => {
        navigate(routes.enterprisePublic(enterpriseId));
    }

    const onSaveClick = () => {
        // TODO save
        navigate(routes.enterprisePublic(enterpriseId));
    }

    useEffect(() => {
        async function loadData(): Promise<void> {

        }
        loadData();
    })

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
                    <StandardFileInput fileName={logoFileName} setFileName={setLogoFileName} />
                </StandardLabeledContainer>
                <StandardLabeledContainer label="Background photo">
                    <StandardFileInput fileName={backgroundPhotoFileName} setFileName={setBackgroundPhotoFileName} />
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