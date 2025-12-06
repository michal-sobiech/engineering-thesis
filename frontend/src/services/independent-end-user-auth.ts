import { UserGroup } from "../common/UserGroup";
import { AuthApi, ResponseError, UserApi } from "../GENERATED-api";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../utils/error";
import { LogInOutcome } from "./LogInOutcome";
import { fetchMyUserGroup } from "./user-group";

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

export async function logInIndependentEndUserWithGroupResolve(email: string, password: string, expectedUserGroup: UserGroup, authApi: AuthApi, userApi: UserApi): Promise<IndependentEndUserLogInOutcome> {
    const logInResponse = await logInIndependentEndUser(email, password, authApi);
    if (logInResponse.status === "BAD_CREDENTIALS") {
        return { status: "BAD_CREDENTIALS" };
    }
    const accessToken = logInResponse.jwt;

    const userGroup = await fetchMyUserGroup(accessToken, userApi);
    if (userGroup !== expectedUserGroup) {
        return { status: "WRONG_USER_GROUP" };
    }

    return { status: "SUCCESS", jwt: accessToken };
}

async function logInIndependentEndUser(email: string, password: string, authApi: AuthApi): Promise<LogInOutcome> {
    const requestParams = { logInIndependentEndUserRequest: { email, password } };
    try {
        const response = await authApi.logInIndependentEndUserRaw(requestParams);
        if (response.raw.status === 200) {
            return { status: "SUCCESS", jwt: (await response.value()).accessToken };
        }
    } catch (error: unknown) {
        if (error instanceof ResponseError && error.response.status === 401) {
            return { status: "BAD_CREDENTIALS" };
        }
    }
    throw new Error(DEFAULT_ERROR_MESSAGE_FOR_USER);
}