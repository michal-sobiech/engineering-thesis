import { useRef } from "react";
import { useNavigate } from "react-router";
import { useLogOut } from "../../auth/useLogOut";
import { routes } from "../../router/routes";
import { toastError } from "../../utils/toast";

export function useAuthorizedApiFetch(): typeof fetch {
    const isUnauthenticatedPopupShown = useRef(false);
    const isUnauthorizedPopupShown = useRef(false);
    const navigate = useNavigate();
    const logOut = useLogOut();

    return async (url: URL | RequestInfo, options?: RequestInit) => {
        const response = await fetch(url, options);
        if (response.status === 401) {
            if (!isUnauthenticatedPopupShown.current) {
                isUnauthenticatedPopupShown.current = true;
                toastError("Unauthenticated. Please log in.", {
                    onClose: () => { isUnauthenticatedPopupShown.current = false; }
                });
            }
            logOut();
            navigate(routes.mainPage);
            return new Promise(() => { });
        } else if (response.status === 403) {
            if (!isUnauthorizedPopupShown.current) {
                isUnauthorizedPopupShown.current = true;
                toastError("Unauthorized.", {
                    onClose: () => { isUnauthorizedPopupShown.current = false; }
                });
            }
            logOut();
            navigate(routes.mainPage);
            return new Promise(() => { });
        }
        return response;
    };
}