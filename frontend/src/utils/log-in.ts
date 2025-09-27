import { removeJwtTokenFromLocalStorage } from "../auth/storage";

export function logOut(): void {
    removeJwtTokenFromLocalStorage();
}
