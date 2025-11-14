import { removeJwtTokenFromLocalStorage } from "../common/local-storage";
import { UnauthenticatedAuth } from "./AuthContext";
import { useAuth } from "./useAuth";

export const useLogOut = () => {
    const { auth, setAuth } = useAuth();

    return () => {
        const newAuth: UnauthenticatedAuth = { isAuthenticated: false };
        setAuth(newAuth);
        console.log("aaaa");
        removeJwtTokenFromLocalStorage();
    }
}