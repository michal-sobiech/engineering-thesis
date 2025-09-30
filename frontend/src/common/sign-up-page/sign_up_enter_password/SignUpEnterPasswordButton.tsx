import { ResultAsync } from "neverthrow";
import { toast } from "react-toastify";
import { toastError } from "../../../utils/toast";
import { useContextOrThrow } from "../../../utils/useContextOrThrow";
import { validatePassword } from "../../../utils/validate-password";
import { StandardButton } from "../../StandardButton";
import { signUpWizardContext } from "../wizard/SignUpWizardContext";

export const SignUpEnterPasswordButton = () => {
    const { incrementStep, email, firstName, lastName, password, createUser } = useContextOrThrow(signUpWizardContext);

    const onClick = async () => {
        const result = await tryToCreateAccount()
        if (!result.isOk()) {
            toastError(result.error.message);
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
