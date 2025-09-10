import { createContext } from "react";
import { Auth } from "./Auth";

export type AuthContextValue = {
    auth: Auth | null,
    setAuth: (auth: Auth) => void,
};

export const authContext = createContext<AuthContextValue | null>(null);