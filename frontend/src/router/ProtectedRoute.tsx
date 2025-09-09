import { FC, ReactNode } from "react";
import { Navigate } from "react-router";
import { authContext } from "../auth-context/storage";
import { routes } from "./routes";

export interface ProtectedRouteProps {
    children: ReactNode;
}

export const ProtectedRoute: FC<ProtectedRouteProps> = ({ children }) => {
    if (authContext !== null) {
        return children;
    } else {
        return <Navigate to={routes.LOG_IN} replace />;
    }
}