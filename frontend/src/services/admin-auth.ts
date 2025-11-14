import { errAsync, okAsync, ResultAsync } from "neverthrow";
import { setJwtTokenInLocalStorage } from "../common/local-storage";
import { AuthApi } from "../GENERATED-api";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../utils/error";
import { errorErrResultAsyncFromPromise } from "../utils/result";
import { LogInStatus } from "./LogInStatus";

export function logInAdmin(username: string, password: string, authApi: AuthApi): ResultAsync<LogInStatus, Error> {
    const requestParams = { logInAdminRequest: { username, password } };
    const promise = authApi.logInAdminRaw(requestParams);

    return errorErrResultAsyncFromPromise(promise)
        .andThen(response => {
            const status = response.raw.status;
            if (status === 200) {
                return errorErrResultAsyncFromPromise(response.value())
                    .map(value => value.accessToken)
                    .map(token => {
                        setJwtTokenInLocalStorage(token);
                        return "LOGGED_IN" as LogInStatus;
                    });
            } else if (status === 401) {
                return okAsync("BAD_CREDENTIALS" as LogInStatus);
            } else {
                return errAsync(new Error(DEFAULT_ERROR_MESSAGE_FOR_USER));
            }
        });
}