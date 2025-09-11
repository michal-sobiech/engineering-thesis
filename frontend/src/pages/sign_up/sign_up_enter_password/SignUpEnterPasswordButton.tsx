import { Button } from "@chakra-ui/react";
import { ResultAsync } from "neverthrow";
import { useState } from "react";
import { toast } from "react-toastify";
import { independentEndUsersApi } from "../../../api/independent-end-users-api";
import { defaultStringErrResultAsyncFromPromise } from "../../../utils/result";
import { toastError } from "../../../utils/toast";
import { useContextOrThrow } from "../../../utils/useContextOrThrow";
import { validatePassword } from "../../../utils/validate-password";
import { signUpWizardContext } from "../wizard/SignUpWizardContext";

export const SignUpEnterPasswordButton = () => {
    const { incrementStep, email, firstName, lastName, password } = useContextOrThrow(signUpWizardContext);
    const [loading, setLoading] = useState<boolean>(false);

    const onClick = async () => {
        const result = await tryToCreateAccount()
        if (!result.isOk()) {
            toastError(result.error);
        } else {
            toast.dismiss();
            incrementStep();
        }
    }

    function tryToCreateAccount(): ResultAsync<void, string> {
        return validatePassword(password)
            .asyncAndThen(createUser);
    }

    function createUser(): ResultAsync<void, string> {
        const promise = independentEndUsersApi.createIndependentEndUser({ email, firstName, lastName, password });
        let result = defaultStringErrResultAsyncFromPromise(promise);
        const voidResult = result.map(() => { });
        return voidResult;
    }

    return <Button
        bg="primary.blue"
        p="5"
        rounded="lg"
        shadow="lg"
        direction="column"
        gap="10px"
        onClick={onClick}
        disabled={loading}>
        Create account
    </Button>;
}
