import { FC, PropsWithChildren, useState } from "react";
import { Auth, AuthContext, AuthContextValue } from "../auth/AuthContext";

export const AuthContextProvider: FC<PropsWithChildren> = ({ children }) => {
    const [auth, setAuth] = useState<Auth>({ isAuthenticated: false });

    const contextValue: AuthContextValue = { auth, setAuth };

    return <AuthContext.Provider value={contextValue}>
        {children}
    </AuthContext.Provider>
}