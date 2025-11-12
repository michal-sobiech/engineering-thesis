import { FC, ReactNode } from "react";
import { Navigate } from "react-router";
import { routes } from "./routes";

export interface ProtectedRouteProps {
    children: ReactNode;
}

export const ProtectedRoute: FC<ProtectedRouteProps> = ({ children }) => {
    // TODO
    // if (authCell === null) {
    if (false) {
        return <Navigate to={routes.mainPage} replace />;
    } else {
        return <>
            {children}
        </>;
    }
}