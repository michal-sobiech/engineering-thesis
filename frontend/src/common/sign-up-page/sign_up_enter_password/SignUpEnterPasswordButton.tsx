import { ResultAsync } from "neverthrow";
import { toast } from "react-toastify";
import { useContextOrThrow } from "../../../hooks/useContextOrThrow";
import { validatePassword } from "../../../services/validate-password";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../../utils/error";
import { toastError } from "../../../utils/toast";
import { StandardButton } from "../../StandardButton";
import { signUpWizardContext } from "../wizard/SignUpWizardContext";

export const SignUpEnterPasswordButton = () => {
    const { incrementStep, email, firstName, lastName, password, createUser } = useContextOrThrow(signUpWizardContext);

    const onClick = async () => {
        const result = await tryToCreateAccount()
        if (!result.isOk()) {
            toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
        } else {
            toast.dismiss();
            incrementStep();
        }
    }

    function tryToCreateAccount(): ResultAsync<void, Error> {
        return validatePassword(password)
            .asyncAndThen(() => createUser({ email, firstName, lastName, password }));
    }

    return <StandardButton onClick={onClick}>
        Create account
    </StandardButton>;
}
