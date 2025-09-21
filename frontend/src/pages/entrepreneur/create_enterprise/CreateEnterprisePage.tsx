import { Center } from "@chakra-ui/react";
import { useState } from "react";
import { generatePath, useNavigate } from "react-router";
import { StandardButton } from "../../../common/StandardButton";
import { StanadardPanel } from "../../../common/StandardPanel";
import { StandardTextArea } from "../../../common/StandardTextArea";
import { StandardTextField } from "../../../common/StandardTextField";
import { routes } from "../../../router/routes";
import { createEnterprise } from "../../../utils/create-enterprise";
import { toastError } from "../../../utils/toast";

export const CreateEnterprisePage = () => {
    const navigate = useNavigate();

    const [enterpriseName, setEnterpriseName] = useState<string>("");
    const [description, setDescription] = useState<string>("");
    const [location, setLocation] = useState<string>("");

    const handleButtonClick = async () => {
        console.log("HELLOOO")

        if (enterpriseName === "") {
            toastError("Choose an enterprise name!");
            return;
        }

        const result = await createEnterprise(enterpriseName, description, location);
        if (result.isErr()) {
            toastError("Couldn't create enterprise. Try again later.");
            return;
        }


        console.log("JEST OK");
        const { enterpriseId } = result.value;
        // TODO put in a separate function?
        console.log("eee");
        const path = generatePath(routes.ENTERPRISE, { enterpriseId })
        console.log(path);
        navigate(path);
    }

    return <Center height="100vh">
        <StanadardPanel>
            <StandardTextField
                text={enterpriseName}
                setText={setEnterpriseName}
                placeholder="Enterprise name"
            />
            <StandardTextArea
                text={description}
                setText={setDescription}
                placeholder="What does your enterprise do?"
            />
            <StandardTextField
                text={location}
                setText={setLocation}
                placeholder="Location"
            />
            <StandardButton onClick={handleButtonClick}>Create enterprise</StandardButton>
        </StanadardPanel>
    </Center>;
};