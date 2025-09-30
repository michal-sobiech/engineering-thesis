import { createContext } from "react";

export interface LogInContextValue {
    email: string,
    password: string,
    landingPageUrl: string,
}

export const logInContext = createContext<LogInContextValue | null>(null);