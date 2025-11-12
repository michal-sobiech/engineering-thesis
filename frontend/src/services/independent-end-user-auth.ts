import { ResultAsync } from "neverthrow";
import { setJwtTokenInLocalStorage } from "../auth/storage";
import { UserGroup } from "../common/UserGroup";
import { AuthApi, UserApi } from "../GENERATED-api";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../utils/error";
import { errorErrResultAsyncFromPromise } from "../utils/result";

export type IndependentEndUserLogInStatus = "SUCCESS" | "BAD_CREDENTIALS" | "GOOD_CREDENTIALS_BUT_INVALID_USER_GROUP";

export function logInIndependentEndUser(email: string, password: string, expectedUserGroup: UserGroup, authApi: AuthApi, userApi: UserApi): ResultAsync<IndependentEndUserLogInStatus, Error> {
    async function createPromise() {
        const requestParams = { logInIndependentEndUserRequest: { email, password } };
        const logInPromise = authApi.logInIndependentEndUserRaw(requestParams);
        const logInResult = await errorErrResultAsyncFromPromise(logInPromise);

        if (logInResult.isErr()) {
            throw new Error(DEFAULT_ERROR_MESSAGE_FOR_USER);
        }

        const status = logInResult.value.raw.status;
        if (status === 401) {
            return "BAD_CREDENTIALS" as IndependentEndUserLogInStatus;
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

        const userGroupPromise = userApi.getMyUserGroup({ headers: { Authorization: `Bearer ${accessToken}` } });
        const userGroupResult = await errorErrResultAsyncFromPromise(userGroupPromise);

        if (userGroupResult.isErr()) {
            throw new Error(DEFAULT_ERROR_MESSAGE_FOR_USER);
        }
        const userGroup = userGroupResult.value.userGroup;

        if (userGroup !== expectedUserGroup) {
            return "GOOD_CREDENTIALS_BUT_INVALID_USER_GROUP" as IndependentEndUserLogInStatus;
        }

        setJwtTokenInLocalStorage(accessToken);
        return "SUCCESS" as IndependentEndUserLogInStatus;
    }

    return errorErrResultAsyncFromPromise(createPromise());
}