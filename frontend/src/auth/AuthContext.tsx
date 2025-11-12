import { createContext } from "react";
import { UserGroup } from "../common/UserGroup";
import { UseStateSetter } from "../utils/use-state";

export interface UnauthenticatedAuth {
    isAuthenticated: false;
}

export interface AuthenticatedAuth {
    isAuthenticated: true;
    jwtToken: string;
    userGroup: UserGroup;
}

export type Auth = UnauthenticatedAuth | AuthenticatedAuth;

export interface AuthContextValue {
    auth: Auth;
    setAuth: UseStateSetter<Auth>;
}

export const AuthContext = createContext<AuthContextValue | null>(null);
