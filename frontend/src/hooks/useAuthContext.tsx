import { useContext, useEffect } from "react";
import { useNavigate } from "react-router";
import { Auth } from "../auth-context/Auth";
import { authContext, AuthContextValue } from "../auth-context/authContext";
import { routes } from "../router/routes";

export function useAuthContext(): AuthContextValue {
    const contextValue = useContext(authContext);
    if (contextValue === null) {
        throw new Error("Auth context value is null");
    }
    return contextValue;
}

export function useRequireAuth(): void {
    useAuthOrRedirect();
}

export function useAuthOrRedirect(): Auth {
    const contextValue = useAuthContext();
    const navigate = useNavigate();

    useEffect(() => {
        if (contextValue.auth === null) {
            navigate(routes.LOG_IN, { replace: true });
        }
    }, [contextValue, navigate]);

    return contextValue.auth!;
}