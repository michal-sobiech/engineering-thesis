import { FC, PropsWithChildren } from "react";
import { Outlet } from "react-router";
import { EnterpriseContextProvider } from "./context/EnterpriseContextProvider";

export const EnterpriseRoute: FC<PropsWithChildren> = ({ children }) => {
    return <EnterpriseContextProvider>
        <Outlet />
    </EnterpriseContextProvider>;
}