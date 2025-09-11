import { err, ok, Result, ResultAsync } from "neverthrow";
import { noAutoAuthMeApi } from "../api/no-Auto-auth-me-api";
import { GetMeResponseEnterpreneur } from "../GENERATED-api";
import { defaultDecodeJwtPayload, JwtToken } from "../utils/jwt";
import { JwtPayload } from "../utils/JwtPayload";
import { defaultStringErrResultAsyncFromPromise, promiseResultToAsyncResult } from "../utils/result";
import { Auth } from "./Auth";
import { EntrepreneurAuth } from "./EntrepreneurAuth";
import { Role } from "./Role";

const localStorageKeys = {
    JWT_TOKEN: "jwtToken",
}

export async function createInitialAuth(): Promise<Result<Auth | null, Error>> {
    const jwtToken = getJwtTokenFromLocalStorage();
    console.log("JWT:", jwtToken);
    if (jwtToken === null) {
        return ok(null);
    }
    return createAuth(jwtToken);
}

export function createAuth(jwtToken: JwtToken): ResultAsync<Auth, Error> {
    return promiseResultToAsyncResult(createAuthAsync(jwtToken), e => new Error(String(e)))
}

async function createAuthAsync(jwtToken: JwtToken): Promise<Result<Auth, Error>> {
    const jwtPayload = defaultDecodeJwtPayload(jwtToken);
    if (jwtPayload.isErr()) {
        return err(jwtPayload.error);
    }

    const me = await fetchMe(jwtToken);
    if (me.isErr()) {
        return err(new Error(me.error));
    }

    const authContext = buildAuthContext(jwtToken, jwtPayload.value, me.value);
    return ok(authContext);
}

export function getJwtTokenFromLocalStorage(): JwtToken | null {
    return localStorage.getItem(localStorageKeys.JWT_TOKEN)
}

export function setJwtTokenInLocalStorage(jwtToken: JwtToken): void {
    localStorage.setItem(localStorageKeys.JWT_TOKEN, jwtToken);
}

export function removeJwtTokenFromLocalStorage(): void {
    localStorage.removeItem(localStorageKeys.JWT_TOKEN)
}

function fetchMe(jwtToken: JwtToken): ResultAsync<GetMeResponseEnterpreneur, string> {
    const initOverrides = {
        headers: {
            Authorization: `Bearer ${jwtToken}`
        }
    };
    const promise = noAutoAuthMeApi.getMe(initOverrides);
    return defaultStringErrResultAsyncFromPromise(promise);
}

function buildAuthContext(jwtToken: JwtToken, jwtPayload: JwtPayload, me: GetMeResponseEnterpreneur): Auth {
    // if (me.role === "ENTREPRENEUR") {
    return buildEntrepreneurAuthContext(jwtToken, jwtPayload, me);
    // }
}

function buildEntrepreneurAuthContext(jwtToken: JwtToken, jwtPayload: JwtPayload, me: GetMeResponseEnterpreneur): EntrepreneurAuth {
    return {
        role: Role.ENTREPRENEUR,
        jwtToken,
        firstName: me.firstName,
        lastName: me.lastName,
        userId: jwtPayload.userId,
        entrepreneurId: me.entrepreneurId,
    };
}
