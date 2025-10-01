import { err, ok, Result } from "neverthrow";
import { defaultDecodeJwt } from "../jwt/jwt";
import { Auth } from "./Auth";

const localStorageKeys = {
    JWT: "jwt",
};

export function createAuth(jwt: string): Result<Auth, Error> {
    const jwtPayload = defaultDecodeJwt(jwt);
    if (jwtPayload.isErr()) {
        return err(jwtPayload.error);
    }

    const auth: Auth = { jwt };
    return ok(auth);
}

export function getJwtFromLocalStorage(): string | null {
    return localStorage.getItem(localStorageKeys.JWT);
}

export function setJwtTokenInLocalStorage(jwtToken: string): void {
    localStorage.setItem(localStorageKeys.JWT, jwtToken);
}

export function removeJwtTokenFromLocalStorage(): void {
    localStorage.removeItem(localStorageKeys.JWT)
}