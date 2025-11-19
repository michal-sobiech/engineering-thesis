import { Box, Flex } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { Outlet, useNavigation } from "react-router";
import { toast, ToastContainer } from "react-toastify";
import { useLoadJwtFromLocalStorage } from "../auth/useLoadJwtFromLocalStorage";
import { GlobalErrorHandler } from "../common/GlobalErrorHandler";
import { Navbar } from "../common/navbar/Navbar";

export function PageLayout() {
    const location = useNavigation();
    const loadJwtFromLocalStorage = useLoadJwtFromLocalStorage();

    const [jwtLoadingFinished, setJwtLoadingFinished] = useState<boolean>(false);

    useEffect(() => {
        removeAllToasts();
    }, [location]);

    useEffect(() => {
        loadJwtFromLocalStorage()
            .map(() => { setJwtLoadingFinished(true) });
    }, []);

    if (!jwtLoadingFinished) {
        return null;
    }

    return (
        <>
            <ToastContainer position="top-center" />
            <GlobalErrorHandler>
                <Flex direction="column">
                    <Box
                        height="8vh"
                        width="100vw">
                        <Navbar />
                    </Box>
                    <Box
                        height="92vh"
                        width="100vw">
                        <Outlet />
                    </Box>
                </Flex>
            </GlobalErrorHandler>
        </>
    );
}

function removeAllToasts(): void {
    toast.dismiss();
}