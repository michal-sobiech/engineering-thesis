import { Button } from "@chakra-ui/react";
import { err, fromPromise, ok, Result } from "neverthrow";
import { useState } from "react";
import { toast } from "react-toastify";
import { independentEndUsersApi } from "../../../api/independent-end-users-api";
import { validateEmail } from "../../../utils/email";
import { fromResult } from "../../../utils/result";
import { useContextOrThrow } from "../../../utils/useContextOrThrow";
import { validatePassword } from "../../../utils/validate-password";
import { signUpWizardContext } from "../wizard/SignUpWizardContext";

export const SignUpEnterPasswordButton = () => {
    const { incrementStep, email, firstName, lastName, password } = useContextOrThrow(signUpWizardContext);
    const [loading, setLoading] = useState<boolean>(false);

    const onClick = async () => {
        setLoading(true);
        const result = tryToCreateAccount()
        incrementStep();
        // sleep
        toast.error("Couldn't create account. Try again later.", { autoClose: 10000 });
        setLoading(false);
    }

    async function tryToCreateAccount(): Promise<Result<void, string>> {
        const fieldsValidationResult = validateFields();
        if (!fieldsValidationResult.isOk()) {
            return fromResult(fieldsValidationResult);
        }

        const promise = createUser();
        return fromPromise(promise, (e) => String(e));
    }

    function validateFields(): Result<void, string> {
        if (email === "") {
            return err("Email cannot be empty");
        }

        if (validateEmail(email)) {
            return err("Given email does not fit email pattern");
        }

        if (password === "") {
            return err("Password cannot be empty");
        }

        const passwordValidationResult = validatePassword(password);
        if (!passwordValidationResult.isOk()) {
            return passwordValidationResult;
        }

        return ok();
    }

    async function createUser(): Promise<void> {
        await independentEndUsersApi.createIndependentEndUser({ email, firstName, lastName, password });
    }

    return <Button
        bg="primary.blue"
        p="5"
        rounded="lg"
        shadow="lg"
        direction="column"
        gap="10px"
        onClick={onClick}>
        Create account
    </Button>;
}
