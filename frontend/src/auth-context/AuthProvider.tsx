import React, { useEffect, useState } from "react";
import { Auth } from "./Auth";
import { authContext, AuthContextValue } from "./authContext";
import { createInitialAuth } from "./storage";

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
    const [auth, setAuth] = useState<Auth | null>(null);
    useEffect(() => {
        createInitialAuth().then(setAuth);
    }, []);
    const contextValue: AuthContextValue = { auth, setAuth };
    console.log("AUTH", auth);

    return <authContext.Provider value={contextValue}>
        {children}
    </authContext.Provider>;
}