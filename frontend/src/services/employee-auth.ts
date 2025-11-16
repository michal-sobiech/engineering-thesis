import { ResultAsync } from "neverthrow";
import { AuthApi, UserApi } from "../GENERATED-api";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../utils/error";
import { errorErrResultAsyncFromPromise } from "../utils/result";
import { LogInOutcome, LogInOutcomeBadCredentials, LogInOutcomeSuccess } from "./LogInOutcome";

export function logInEmployee(enterpriseId: number, username: string, password: string, authApi: AuthApi, userApi: UserApi): ResultAsync<LogInOutcome, Error> {
    async function createPromise() {
        const requestParams = { logInEnterpriseEmployeeRequest: { enterpriseId, username, password } };
        const promise = authApi.logInEnterpriseEmployeeRaw(requestParams);
        const logInResult = await errorErrResultAsyncFromPromise(promise);

        if (logInResult.isErr()) {
            throw new Error(DEFAULT_ERROR_MESSAGE_FOR_USER);
        }

        const status = logInResult.value.raw.status;
        if (status === 401) {
            return { status: "BAD_CREDENTIALS" } as LogInOutcomeBadCredentials;
        }
        if (status !== 200) {
            throw new Error(DEFAULT_ERROR_MESSAGE_FOR_USER);
        }

        const accessTokenPromise = logInResult.value.value();
        const accessTokenResult = await errorErrResultAsyncFromPromise(accessTokenPromise);

        if (accessTokenResult.isErr()) {
            throw new Error(DEFAULT_ERROR_MESSAGE_FOR_USER);
        }
        const accessToken = accessTokenResult.value.accessToken;

        return {
            status: "SUCCESS",
            jwt: accessToken,
        } as LogInOutcomeSuccess;
    }

    return errorErrResultAsyncFromPromise(createPromise());
}