import { errAsync, ok, okAsync, Result, ResultAsync } from "neverthrow";
import { useNavigate } from "react-router";
import { authApi } from "../../api/auth-api";
import { userApi } from "../../api/user-api";
import { setJwtTokenInLocalStorage } from "../../auth/storage";
import { useContextOrThrow } from "../../hooks/useContextOrThrow";
import { errorErrResultAsyncFromPromise, promiseResultToErrorAsyncResult } from "../../utils/result";
import { toastError } from "../../utils/toast";
import { StandardButton } from "../StandardButton";
import { logInContext } from "./LogInContext";

export const LogInButton = () => {
    const { email, password, userGroup, landingPageUrl } = useContextOrThrow(logInContext);
    const navigate = useNavigate();

    function logIn(email: string, password: string): ResultAsync<Auth | null, Error> {
        async function createPromise(): Promise<Result<Auth | null, Error>> {
            const response = await errorErrResultAsyncFromPromise(authApi.logInIndependentEndUser({ email, password }));
            if (response.isErr()) {
                return errAsync(response.error);
            }
            const accessToken = response.value.accessToken;

            const myUserGroupResponse = await errorErrResultAsyncFromPromise(userApi.getMyUserGroup({ headers: { Authorization: `Bearer ${accessToken}` } }));
            if (myUserGroupResponse.isErr()) {
                return errAsync(myUserGroupResponse.error);
            }

            const myUserGroup = myUserGroupResponse.value.userGroup;
            if (myUserGroup !== userGroup) {
                return okAsync(null);
            }

            setJwtTokenInLocalStorage(accessToken);
            const auth: Auth = { jwt: accessToken };
            authCell.value = auth;
            return ok(auth);
        }
        return promiseResultToErrorAsyncResult(createPromise());
    }

    const onClick = async () => {
        const result = await logIn(email, password);
        if (result.isOk() && result.value !== null) {
            navigate(landingPageUrl);
        } else {
            toastError("Couldn't log in. Try again later.");
        }
    }

    return <StandardButton onClick={onClick} >
        Log in
    </StandardButton>
}