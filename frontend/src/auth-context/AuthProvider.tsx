import React, { useEffect, useState } from "react";
import { fromNullable, fromResult } from "../utils/result";
import { Auth } from "./Auth";
import { authContext, AuthContextValue } from "./authContext";
import { createAuth, getJwtTokenFromLocalStorage } from "./storage";

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
    const [auth, setAuth] = useState<Auth | null>(null);
    useEffect(() => {
        fromResult(fromNullable(getJwtTokenFromLocalStorage()))
            .andThen(createAuth)
            .map(setAuth);
    }, []);
    const contextValue: AuthContextValue = { auth, setAuth };
    console.log("AUTH", auth);

    return <authContext.Provider value={contextValue}>
        {children}
    </authContext.Provider>;
}