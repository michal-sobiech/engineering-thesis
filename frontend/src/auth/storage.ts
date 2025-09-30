import { errAsync, okAsync, ResultAsync } from "neverthrow";
import { defaultDecodeJwt } from "../jwt/jwt";
import { Auth } from "./Auth";

const localStorageKeys = {
    JWT_TOKEN: "jwtToken",
}

export function createAuth(jwtToken: string): ResultAsync<Auth, Error> {
    const jwtPayload = defaultDecodeJwt(jwtToken);
    if (jwtPayload.isErr()) {
        return errAsync(jwtPayload.error);
    }

    const auth: Auth = { jwtToken };
    return okAsync(auth);
}

export function getJwtTokenFromLocalStorage(): string | null {
    return localStorage.getItem(localStorageKeys.JWT_TOKEN)
}

export function setJwtTokenInLocalStorage(jwtToken: string): void {
    localStorage.setItem(localStorageKeys.JWT_TOKEN, jwtToken);
}

export function removeJwtTokenFromLocalStorage(): void {
    localStorage.removeItem(localStorageKeys.JWT_TOKEN)
}