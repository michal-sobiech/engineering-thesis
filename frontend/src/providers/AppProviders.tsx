import { FC, PropsWithChildren } from "react";
import { AuthContextProvider } from "./AuthContextProvider";

export const AppProviders: FC<PropsWithChildren> = ({ children }) => {
    return <AuthContextProvider>
        {children}
    </AuthContextProvider >
}