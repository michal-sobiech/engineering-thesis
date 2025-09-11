import { ResultAsync } from "neverthrow";
import { useNavigate } from "react-router";
import { authApi } from "../../../api/auth-api";
import { createAuth, setJwtTokenInLocalStorage } from "../../../auth-context/storage";
import { StandardButton } from "../../../common/StandardButton";
import { useAuthContext } from "../../../hooks/useAuthContext";
import { routes } from "../../../router/routes";
import { errorErrResultAsyncFromPromise } from "../../../utils/result";
import { toastError } from "../../../utils/toast";
import { useContextOrThrow } from "../../../utils/useContextOrThrow";
import { logInContext } from "../LogInContext";

export const LogInButton = () => {
    const { email, password } = useContextOrThrow(logInContext);
    const { setAuth } = useAuthContext();
    const navigate = useNavigate();

    function logIn(email: string, password: string): ResultAsync<void, Error> {
        const promise = authApi.logInIndependentEndUser({ email, password });

        return errorErrResultAsyncFromPromise(promise)
            .map(logInResponse => logInResponse.accessToken)
            .andTee(setJwtTokenInLocalStorage)
            .andThen(createAuth)
            .map(setAuth);
    }

    const onClick = async () => {
        const result = await logIn(email, password);
        console.log("Result isOk:", result.isOk());
        if (result.isOk()) {
            navigate(routes.ENTREPRENEUR_LANDING_PAGE, { replace: true });
        } else {
            toastError("Couldn't log in. Try again later.");
        }
    }

    return <StandardButton onClick={onClick} >
        Log in
    </StandardButton>
}