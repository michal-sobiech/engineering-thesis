import { errAsync, ResultAsync } from "neverthrow";
import { authApi } from "../api/auth-api";
import { setJwtTokenInLocalStorage } from "../auth/storage";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../utils/error";
import { errorErrResultAsyncFromPromise } from "../utils/result";

export function logInAdmin(username: string, password: string): ResultAsync<void, Error> {
    const requestParams = { logInAdminRequest: { username, password } };
    const promise = authApi.logInAdminRaw(requestParams);
    return errorErrResultAsyncFromPromise(promise)
        .andThen(response => {
            const status = response.raw.status;
            if (status === 200) {
                return errorErrResultAsyncFromPromise(response.value());
            } else if (status === 401) {
                return errAsync(new Error("Invalid credentials"));
            } else {
                return errAsync(new Error(DEFAULT_ERROR_MESSAGE_FOR_USER));
            }
        })
        .map(value => value.accessToken)
        .map(setJwtTokenInLocalStorage);
}