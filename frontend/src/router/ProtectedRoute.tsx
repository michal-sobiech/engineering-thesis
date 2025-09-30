import { FC, ReactNode } from "react";
import { Navigate } from "react-router";
import { authCell } from "../auth/AuthProvider";
import { routes } from "./routes";

export interface ProtectedRouteProps {
    children: ReactNode;
}

export const ProtectedRoute: FC<ProtectedRouteProps> = ({ children }) => {
    console.log("Protected route")
    console.log("Auth here", authCell);
    if (authCell === null) {
        return <Navigate to={routes.logIn} replace />;
    } else {
        return <>
            {children}
        </>;
    }
}