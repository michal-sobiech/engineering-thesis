import { useNavigate } from "react-router";
import { Auth } from "../auth-context/Auth";
import { routes } from "../router/routes";
import { useAuthContext } from "./useAuthContext";

export function useWithAuth(): (func: (auth: Auth) => unknown) => void {
    const authContextValue = useAuthContext();
    const navigate = useNavigate();

    return (func: (auth: Auth) => unknown) => {
        if (authContextValue.auth === null) {
            console.log("NULL!!!");
            navigate(routes.LOG_IN, { replace: true });
        } else {
            console.log(authContextValue.auth)
            func(authContextValue.auth);
        }
    }
}