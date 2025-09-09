import { ResultAsync } from "neverthrow";
import { StandardButton } from "../../../common/StandardButton";
import { logInAndSetJwtToken } from "../../../utils/log-in";
import { createDefaultResultAsyncfromPromise } from "../../../utils/result";
import { toastError } from "../../../utils/toast";
import { useContextOrThrow } from "../../../utils/useContextOrThrow";
import { logInContext } from "../LogInContext";

export const LogInButton = () => {
    const { email, password } = useContextOrThrow(logInContext);

    const onClick = async () => {
        const result = await logIn(email, password);
        if (!result.isOk()) {
            toastError("Couldn't log in. Try again later.");
        }
    }

    return <StandardButton onClick={onClick} >
        Log in
    </StandardButton>
}

function logIn(email: string, password: string): ResultAsync<void, string> {
    const promise = logInAndSetJwtToken(email, password);
    return createDefaultResultAsyncfromPromise(promise);
}