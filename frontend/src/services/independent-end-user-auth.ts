import { ResultAsync } from "neverthrow";
import { UserGroup } from "../common/UserGroup";
import { AuthApi, UserApi } from "../GENERATED-api";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../utils/error";
import { errorErrResultAsyncFromPromise } from "../utils/result";

export interface IndependentEndUserLogInOutcomeSuccess {
    status: "SUCCESS",
    jwt: string,
}

export interface IndependentEndUserLogInOutcomeBadCredentials {
    status: "BAD_CREDENTIALS",
}

export interface IndependentEndUserLogInOutcomeWrongUserGroup {
    status: "WRONG_USER_GROUP",
}

export type IndependentEndUserLogInOutcome = IndependentEndUserLogInOutcomeSuccess | IndependentEndUserLogInOutcomeBadCredentials | IndependentEndUserLogInOutcomeWrongUserGroup;

export function logInIndependentEndUser(email: string, password: string, expectedUserGroup: UserGroup, authApi: AuthApi, userApi: UserApi): ResultAsync<IndependentEndUserLogInOutcome, Error> {
    async function createPromise() {
        const requestParams = { logInIndependentEndUserRequest: { email, password } };
        const logInPromise = authApi.logInIndependentEndUserRaw(requestParams);
        const logInResult = await errorErrResultAsyncFromPromise(logInPromise);

        if (logInResult.isErr()) {
            throw new Error(DEFAULT_ERROR_MESSAGE_FOR_USER);
        }

        const status = logInResult.value.raw.status;
        if (status === 401) {
            return { status: "BAD_CREDENTIALS" } as IndependentEndUserLogInOutcomeBadCredentials;
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
            return { status: "WRONG_USER_GROUP" } as IndependentEndUserLogInOutcomeWrongUserGroup;
        }

        return {
            status: "SUCCESS",
            jwt: accessToken,
        } as IndependentEndUserLogInOutcomeSuccess;
    }

    return errorErrResultAsyncFromPromise(createPromise());
}