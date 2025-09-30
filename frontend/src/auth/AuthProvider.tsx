import { okAsync, ResultAsync } from "neverthrow";
import { defaultDecodeJwt } from "../jwt/jwt";
import { Cell } from "../utils/Cell";
import { assertDefined } from "../utils/result";
import { Auth } from "./Auth";
import { createAuth, getJwtTokenFromLocalStorage } from "./storage";

export let authCell: Cell<Auth | null> = { value: null };

export function loadAndSetAuth(): ResultAsync<void, Error> {
    return loadAuth()
        .map(authValue => { authCell.value = authValue });
}

function loadAuth(): ResultAsync<Auth | null, Error> {
    return okAsync(getJwtTokenFromLocalStorage())
        .andThen(jwtToken => jwtToken !== null ? createAuth(jwtToken) : okAsync(null));
}

export function getUserIdOrThrow(): number {
    const userId = assertDefined(authCell.value)
        .map(auth => auth.jwtToken)
        .andThen(defaultDecodeJwt)
        .map(jwtPayload => jwtPayload.userId);
    if (userId.isOk()) {
        return userId.value;
    } else {
        throw new Error("Couldn't retrieve user id");
    }
}