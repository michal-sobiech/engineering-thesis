import { err, ok, Result, ResultAsync } from "neverthrow";
import { noAutoAuthMeApi } from "../api/no-auto-auth-me-api";
import { GetMeResponseEnterpreneur } from "../GENERATED-api";
import { defaultDecodeJwtPayload, JwtToken } from "../utils/jwt";
import { JwtPayload } from "../utils/JwtPayload";
import { createDefaultResultAsyncfromPromise } from "../utils/result";
import { AuthContext } from "./AuthContext";
import { EntrepreneurAuthContext } from "./EntrepreneurAuthContext";
import { Role } from "./Role";

const localStorageKeys = {
    JWT_TOKEN: "jwtToken",
}

export let authContext: AuthContext | null = null;

export async function initStorage(): Promise<void> {
    const jwtToken = getJwtTokenFromLocalStorage();
    if (jwtToken !== null) {
        updateStorage(jwtToken);
    }
}

export async function updateStorage(jwtToken: JwtToken): Promise<void> {
    const result = await createAuthContext(jwtToken);
    if (result.isErr()) {
        console.log(result.error);
    }
    authContext = result.isOk() ? result.value : null;
    console.log(authContext);
}

export function emptyStorage(): void {
    authContext = null;
}

async function createAuthContext(jwtToken: JwtToken): Promise<Result<AuthContext, Error>> {
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

function getJwtTokenFromLocalStorage(): JwtToken | null {
    return localStorage.getItem(localStorageKeys.JWT_TOKEN)
}

function fetchMe(jwtToken: JwtToken): ResultAsync<GetMeResponseEnterpreneur, string> {
    const initOverrides = {
        headers: {
            Authorization: `Bearer ${jwtToken}`
        }
    };
    const promise = noAutoAuthMeApi.getMe(initOverrides);
    return createDefaultResultAsyncfromPromise(promise);
}

function buildAuthContext(jwtToken: JwtToken, jwtPayload: JwtPayload, me: GetMeResponseEnterpreneur): AuthContext {
    // if (me.role === "ENTREPRENEUR") {
    return buildEntrepreneurAuthContext(jwtToken, jwtPayload, me);
    // }
}

function buildEntrepreneurAuthContext(jwtToken: JwtToken, jwtPayload: JwtPayload, me: GetMeResponseEnterpreneur): EntrepreneurAuthContext {
    return {
        role: Role.ENTREPRENEUR,
        jwtToken,
        userId: jwtPayload.userId,
        entrepreneurId: me.entrepreneurId,
    };
}
