import { Center, Flex, Heading } from "@chakra-ui/react";
import { useState } from "react";
import { useNavigate } from "react-router";
import { useRegularAdminsApi } from "../../../api/regular-admins-api";
import { StandardButton } from "../../../common/StandardButton";
import { StandardFlex } from "../../../common/StandardFlex";
import { StandardLabeledContainer } from "../../../common/StandardLabeledContainer";
import { StandardPanel } from "../../../common/StandardPanel";
import { StandardTextField } from "../../../common/StandardTextField";
import { CreateRegularAdminRequest, ResponseError } from "../../../GENERATED-api";
import { routes } from "../../../router/routes";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../../utils/error";
import { toastError, toastSuccess } from "../../../utils/toast";

export const CreateRegularAdminPage = () => {
    const regularAdminsApi = useRegularAdminsApi();
    const navigate = useNavigate();

    const [username, setUsername] = useState<string>("");
    const [firstName, setFirstName] = useState<string>("");
    const [lastName, setLastName] = useState<string>("");
    const [password1, setPassword1] = useState<string>("");
    const [password2, setPassword2] = useState<string>("");

    const onDiscardClick = () => {
        navigate(routes.regularAdminListPage);
    }

    const onSaveClick = async () => {
        if (username === "") {
            toastError("Choose a username");
            return;
        }

        if (firstName === "") {
            toastError("Enter first name");
            return;
        }

        if (lastName === "") {
            toastError("Enter last name");
            return;
        }

        if (password1 === "") {
            toastError("Enter password");
            return;
        }

        if (password2 === "") {
            toastError("Confirm your password");
            return;
        }

        if (password1 !== password2) {
            toastError("Passwords don't match!");
            return;
        }

        const request: CreateRegularAdminRequest = {
            username,
            firstName,
            lastName,
            password: password1
        };

        try {
            await regularAdminsApi.createRegularAdmin(request);
            toastSuccess("Created admin!");
            navigate(routes.regularAdminListPage);
        } catch (error: unknown) {
            if (error instanceof ResponseError && error.response.status == 409) {
                toastError("Chosen username is already taken");
            } else {
                toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
            }
        }
    }

    return <Center height="100%">
        <StandardPanel height="100%" width="80%">
            <StandardFlex height="100%">
                <Heading>Create admin</Heading>
                <StandardLabeledContainer label="Username">
                    <StandardTextField text={username} setText={setUsername} />
                </StandardLabeledContainer>
                <StandardLabeledContainer label="First name">
                    <StandardTextField text={firstName} setText={setFirstName} />
                </StandardLabeledContainer>
                <StandardLabeledContainer label="Last name">
                    <StandardTextField text={lastName} setText={setLastName} />
                </StandardLabeledContainer>
                <StandardLabeledContainer label="Password">
                    <StandardTextField text={password1} setText={setPassword1} type="password" placeholder="New password" />
                </StandardLabeledContainer>
                <StandardLabeledContainer label="Confirm password">
                    <StandardTextField text={password2} setText={setPassword2} type="password" placeholder="Confirm new password" />
                </StandardLabeledContainer>
                <Flex direction="row" gap="5px">
                    <StandardButton
                        onClick={onDiscardClick}
                        backgroundColor="primary.darkRed"
                        flex={1}>
                        Discard
                    </StandardButton>
                    <StandardButton
                        onClick={onSaveClick}
                        backgroundColor="primary.lightGreen"
                        flex={1}>
                        Save
                    </StandardButton>
                </Flex>
            </StandardFlex>
        </StandardPanel>
    </Center>;
}