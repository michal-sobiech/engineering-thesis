import { errAsync, okAsync, ResultAsync } from "neverthrow";
import { useAuthApi } from "../api/auth-api";
import { setJwtTokenInLocalStorage } from "../auth/storage";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../utils/error";
import { errorErrResultAsyncFromPromise } from "../utils/result";
import { LogInStatus } from "./LogInStatus";

export function logInEmployee(enterpriseId: number, username: string, password: string): ResultAsync<LogInStatus, Error> {
    const authApi = useAuthApi();
    const requestParams = { logInEnterpriseEmployeeRequest: { enterpriseId, username, password } };
    const promise = authApi.logInEnterpriseEmployeeRaw(requestParams);

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