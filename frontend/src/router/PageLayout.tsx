import { useEffect } from "react";
import { Outlet, useNavigation } from "react-router";
import { toast, ToastContainer } from "react-toastify";

export function PageLayout() {
    const location = useNavigation();

    useEffect(() => removeAllToasts(), [location]);

    return (
        <>
            <ToastContainer position="top-center" />
            <Outlet />
        </>
    );
}

function removeAllToasts(): void {
    toast.dismiss();
}