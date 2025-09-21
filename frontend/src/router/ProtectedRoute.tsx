import { FC, ReactNode } from "react";
import { Navigate } from "react-router";
import { auth } from "../auth-context/AuthProvider";
import { routes } from "./routes";

export interface ProtectedRouteProps {
    children: ReactNode;
}

export const ProtectedRoute: FC<ProtectedRouteProps> = ({ children }) => {
    console.log("Protected route")
    console.log("Auth here", auth);
    if (auth === null) {
        return <Navigate to={routes.LOG_IN} replace />;
    } else {
        return <>
            {children}
        </>;
    }
}