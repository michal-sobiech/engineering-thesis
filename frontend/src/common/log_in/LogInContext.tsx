import { createContext } from "react";
import { UserGroup } from "../UserGroup";

export interface LogInContextValue {
    email: string,
    password: string,
    userGroup: UserGroup,
    landingPageUrl: string,
}

export const logInContext = createContext<LogInContextValue | null>(null);