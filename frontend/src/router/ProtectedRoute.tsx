import { FC, ReactNode } from "react";
import { useRequireAuthContext } from "../hooks/useAuthContext";

export interface ProtectedRouteProps {
    children: ReactNode;
}

export const ProtectedRoute: FC<ProtectedRouteProps> = ({ children }) => {
    useRequireAuthContext();
    return <>
        {children}
    </>;
}