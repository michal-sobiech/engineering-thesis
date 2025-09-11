import { Auth } from "../auth-context/Auth";
import { useAuthContext } from "./useAuthContext";

export function useUpdateAuth(): (auth: Auth) => void {
    const { setAuth } = useAuthContext();

    const updater = (auth: Auth) => {
        setAuth(auth);
    }

    return updater;
}