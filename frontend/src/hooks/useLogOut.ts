import { UnauthenticatedAuth } from "../auth/AuthContext";
import { useAuth } from "../auth/useAuth";

export function useLogOut() {
    const { auth, setAuth } = useAuth();

    return () => {
        if (auth.isAuthenticated) {
            const newAuth: UnauthenticatedAuth = { isAuthenticated: false }
            setAuth(newAuth);
        }
    }
}