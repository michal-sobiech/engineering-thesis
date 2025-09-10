import { FC, ReactNode } from "react";
import { Navigate } from "react-router";
import { useAuthContext } from "../hooks/useAuthContext";
import { routes } from "./routes";

export interface ProtectedRouteProps {
    children: ReactNode;
}

export const ProtectedRoute: FC<ProtectedRouteProps> = ({ children }) => {
    const { auth } = useAuthContext();
    if (auth !== null) {
        return <>
            {children}
        </>;
    } else {
        return <Navigate to={routes.LOG_IN} />;
    }
}