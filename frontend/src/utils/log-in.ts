import { authApi } from "../api/auth-api";
import { JwtToken, jwtTokenLocalStorageKey } from "./JwtToken";

export async function logInAndSetJwtToken(email: string, password: string): Promise<void> {
    const { accessToken } = await authApi.logInIndependentEndUser({ email, password });
    localStorage.setItem(jwtTokenLocalStorageKey, accessToken);
}

export function getJwtToken(): JwtToken | null {
    return localStorage.getItem(jwtTokenLocalStorageKey);
}