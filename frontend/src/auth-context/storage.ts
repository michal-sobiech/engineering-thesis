import { err, ok, Result, ResultAsync } from "neverthrow";
import { noAutoAuthMeApi } from "../api/no-Auto-auth-me-api";
import { GetMeResponseEnterpreneur } from "../GENERATED-api";
import { defaultDecodeJwtPayload, JwtToken } from "../utils/jwt";
import { JwtPayload } from "../utils/JwtPayload";
import { createDefaultResultAsyncfromPromise } from "../utils/result";
import { Auth } from "./Auth";
import { EntrepreneurAuthContext } from "./EntrepreneurAuthContext";
import { Role } from "./Role";

const localStorageKeys = {
    JWT_TOKEN: "jwtToken",
}

export async function createInitialAuth(): Promise<Auth | null> {
    const jwtToken = getJwtTokenFromLocalStorage();
    if (jwtToken === null) {
        return null;
    }
    return updateStorage(jwtToken);
}

export async function updateStorage(jwtToken: JwtToken): Promise<Auth | null> {
    const result = await createAuthUsingJwt(jwtToken);
    if (result.isErr()) {
        console.log(result.error);
        return null;
    }
    return result.value;
}

async function createAuthUsingJwt(jwtToken: JwtToken): Promise<Result<Auth, Error>> {
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

function buildAuthContext(jwtToken: JwtToken, jwtPayload: JwtPayload, me: GetMeResponseEnterpreneur): Auth {
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
