import { ok, Result } from "neverthrow";
import { Cell } from "../utils/Cell";
import { Auth } from "./Auth";
import { createAuth, getJwtFromLocalStorage } from "./storage";

export let authCell: Cell<Auth | null> = { value: null };

export function loadAndSetAuth(): Result<void, Error> {
    return loadAuth()
        .map(authValue => { authCell.value = authValue });
}

function loadAuth(): Result<Auth | null, Error> {
    return ok(getJwtFromLocalStorage())
        .andThen(jwtToken => jwtToken !== null ? createAuth(jwtToken) : ok(null));
}
