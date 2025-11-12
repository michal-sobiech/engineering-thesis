import { createContext } from "react";
import { IndependentEndUserGroup } from "../UserGroup";

export interface IndependentEndUserLogInContextValue {
    email: string,
    password: string,
    userGroup: IndependentEndUserGroup,
    landingPageUrl: string,
}

export const logInContext = createContext<IndependentEndUserLogInContextValue | null>(null);