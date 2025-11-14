const localStorageKeys = {
    JWT: "jwt"
};

export function getJwtFromLocalStorage(): string | null {
    return localStorage.getItem(localStorageKeys.JWT);
}

export function setJwtTokenInLocalStorage(jwtToken: string): void {
    localStorage.setItem(localStorageKeys.JWT, jwtToken);
}

export function removeJwtTokenFromLocalStorage(): void {
    localStorage.removeItem(localStorageKeys.JWT)
}