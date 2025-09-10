import { useContext } from "react";
import { authContext } from "../auth-context/authContext";

export function useAuthContext() {
    const context = useContext(authContext);
    if (context === null) {
        throw new Error("Context is null");
    }
    return context;
}