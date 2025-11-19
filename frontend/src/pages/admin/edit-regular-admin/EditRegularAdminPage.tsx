import { Center, Heading } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { useRegularAdminsApi } from "../../../api/regular-admins-api";
import { StandardButton } from "../../../common/StandardButton";
import { StandardFlex } from "../../../common/StandardFlex";
import { StandardLabeledContainer } from "../../../common/StandardLabeledContainer";
import { StandardTextField } from "../../../common/StandardTextField";
import { PatchRegularAdminRequest } from "../../../GENERATED-api";
import { useIntParam } from "../../../hooks/useIntParam";
import { routes } from "../../../router/routes";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../../utils/error";
import { errorErrResultAsyncFromPromise } from "../../../utils/result";
import { toastError } from "../../../utils/toast";

export const EditRegularAdminPage = () => {
    const adminUserId = useIntParam("userId");
    const regularAdminsApi = useRegularAdminsApi();
    const navigate = useNavigate();

    const [username, setUsername] = useState<string>("");
    const [firstName, setFirstName] = useState<string>("");
    const [lastName, setLastName] = useState<string>("");
    const [password1, setPassword1] = useState<string>("");
    const [password2, setPassword2] = useState<string>("");

    useEffect(() => {
        async function loadData() {
            const promise = regularAdminsApi.getRegularAdmin(adminUserId);
            const resultAsync = errorErrResultAsyncFromPromise(promise);
            const result = await resultAsync;

            if (result.isErr()) {
                toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
                navigate(routes.mainPage);
                return;
            }

            setUsername(result.value.username);
            setFirstName(result.value.firstName);
            setLastName(result.value.lastName);
        }
        loadData();
    })

    const onSaveClick = () => {
        if (password1 !== password2) {
            toastError("Passwords don't match!");
            return;
        }

        const request: PatchRegularAdminRequest = {
            username: username === "" ? undefined : username,
            firstName: firstName === "" ? undefined : firstName,
            lastName: lastName === "" ? undefined : lastName,
            password: password1 === "" ? undefined : password1
        };
        regularAdminsApi.patchRegularAdmin(adminUserId, request);
    }

    return <Center height="100%">
        <StandardFlex>
            <Heading>Edit admin</Heading>
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
            <StandardButton onClick={onSaveClick}>
                Save
            </StandardButton>
        </StandardFlex>
    </Center>;
}