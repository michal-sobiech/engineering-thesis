import { ResultAsync } from "neverthrow";
import { useNavigate } from "react-router";
import { authApi } from "../../api/auth-api";
import { Auth } from "../../auth/Auth";
import { authCell } from "../../auth/AuthProvider";
import { createAuth, setJwtTokenInLocalStorage } from "../../auth/storage";
import { routes } from "../../router/routes";
import { errorErrResultAsyncFromPromise } from "../../utils/result";
import { toastError } from "../../utils/toast";
import { useContextOrThrow } from "../../utils/useContextOrThrow";
import { StandardButton } from "../StandardButton";
import { logInContext } from "./LogInContext";

export const LogInButton = () => {
    const { email, password, landingPageUrl } = useContextOrThrow(logInContext);
    const navigate = useNavigate();

    function logIn(email: string, password: string): ResultAsync<Auth | null, Error> {
        const promise = authApi.logInIndependentEndUser({ email, password });

        return errorErrResultAsyncFromPromise(promise)
            .map(logInResponse => logInResponse.accessToken)
            .andTee(setJwtTokenInLocalStorage)
            .andThen(createAuth)
            .map(newAuth => authCell.value = newAuth);
    }

    const onClick = async () => {
        const result = await logIn(email, password);
        if (result.isOk()) {
            navigate(routes.entrepreneurLandingPage, { replace: true });
        } else {
            toastError("Couldn't log in. Try again later.");
        }
    }

    return <StandardButton onClick={onClick} >
        Log in
    </StandardButton>
}