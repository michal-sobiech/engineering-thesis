import { ReactNode } from "react";

export const AppProviders = ({ children }: { children: ReactNode }) => {
    // return <EnterpriseContextProvider>
    //     {children}
    // </EnterpriseContextProvider>;

    return <>
        {children}
    </>
}