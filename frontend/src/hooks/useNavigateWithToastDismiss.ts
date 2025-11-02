import { NavigateFunction, useNavigate } from "react-router";
import { toast } from "react-toastify";

export function useNavigateWithToastDismiss(): NavigateFunction {
    const navigate = useNavigate();

    return ((...args: Parameters<NavigateFunction>) => {
        toast.dismiss();
        navigate(...args);
    }) as NavigateFunction;
}