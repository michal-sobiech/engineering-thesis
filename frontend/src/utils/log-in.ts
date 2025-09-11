import { removeJwtTokenFromLocalStorage } from "../auth-context/storage";


export function logOut(): void {
    removeJwtTokenFromLocalStorage();
}
