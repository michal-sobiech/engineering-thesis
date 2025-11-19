import { ChakraProvider } from "@chakra-ui/react";
import { FC, PropsWithChildren } from "react";
import { system } from "../config/system-config";
import { AuthContextProvider } from "./AuthContextProvider";

export const AppProviders: FC<PropsWithChildren> = ({ children }) => {
    return <AuthContextProvider>
        <ChakraProvider value={system}>
            {children}
        </ChakraProvider>
    </AuthContextProvider >
}