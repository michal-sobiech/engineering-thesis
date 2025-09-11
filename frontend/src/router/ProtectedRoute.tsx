import { FC, ReactNode } from "react";
import { Navigate } from "react-router";
import { useAuthContext } from "../hooks/useAuthContext";
import { routes } from "./routes";

export interface ProtectedRouteProps {
    children: ReactNode;
}

export const ProtectedRoute: FC<ProtectedRouteProps> = ({ children }) => {
    const authContext = useAuthContext();

    console.log("Protected route")
    if (authContext.auth === null) {
        return <Navigate to={routes.LOG_IN} replace />;
    } else {
        return <>
            {children}
        </>;
    }
}