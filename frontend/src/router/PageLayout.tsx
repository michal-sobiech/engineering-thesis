import { Box, Flex } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { Outlet, useNavigation } from "react-router";
import { toast, ToastContainer } from "react-toastify";
import { useLoadJwtFromLocalStorage } from "../auth/useLoadJwtFromLocalStorage";
import { Navbar } from "../common/navbar/Navbar";

export function PageLayout() {
    const location = useNavigation();

    const loadJwtFromLocalStorage = useLoadJwtFromLocalStorage();

    const [jwtLoadingFinished, setJwtLoadingFinished] = useState<boolean>(false);

    useEffect(() => {
        removeAllToasts();
    }, [location]);

    useEffect(() => {
        loadJwtFromLocalStorage().then(() => setJwtLoadingFinished(true));
    }, []);

    if (!jwtLoadingFinished) {
        return null;
    }

    return (
        <>
            <ToastContainer position="top-center" />
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
        </>
    );
}

function removeAllToasts(): void {
    toast.dismiss();
}