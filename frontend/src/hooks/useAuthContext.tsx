import { useContext } from "react";
import { authContext, AuthContextValue } from "../auth-context/authContext";

export function useAuthContext(): AuthContextValue {
    const contextValue = useContext(authContext);
    if (contextValue === null) {
        throw new Error("Auth context value is null");
    }
    return contextValue;
}