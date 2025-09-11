import { useNavigate } from "react-router";
import { AuthContextValue } from "../auth-context/authContext";
import { routes } from "../router/routes";
import { useAuthContext } from "./useAuthContext";

export function useWithAuth(): (func: (authContextValue: AuthContextValue) => unknown) => void {
    const authContextValue = useAuthContext();
    const navigate = useNavigate();

    return (func: (authContextValue: AuthContextValue) => unknown) => {
        if (authContextValue.auth === null) {
            navigate(routes.LOG_IN, { replace: true });
        } else {
            func(authContextValue);
        }
    }
}