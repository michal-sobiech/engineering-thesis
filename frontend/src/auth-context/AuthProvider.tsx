import { okAsync, ResultAsync } from "neverthrow";
import { Cell } from "../utils/Cell";
import { Auth } from "./Auth";
import { createAuth, getJwtTokenFromLocalStorage } from "./storage";

export let auth: Cell<Auth | null> = { value: null };

export function loadAndSetAuth(): ResultAsync<void, Error> {
    return loadAuth()
        .map(authValue => { auth.value = authValue });
}

function loadAuth(): ResultAsync<Auth | null, Error> {
    return okAsync(getJwtTokenFromLocalStorage())
        .andThen(jwtToken => jwtToken !== null ? createAuth(jwtToken) : okAsync(null));
}
