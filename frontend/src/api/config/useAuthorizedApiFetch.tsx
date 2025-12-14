import { useRef } from "react";
import { useNavigate } from "react-router";
import { useLogOut } from "../../auth/useLogOut";
import { routes } from "../../router/routes";
import { UnauthenticatedError } from "../../utils/error/UnauthenticatedError";
import { UnauthorizedError } from "../../utils/error/UnauthorizedError";
import { toastError } from "../../utils/toast";
import { authorizedApiFetch } from "./authorized-api-fetch";

export function useAuthorizedApiFetch(): typeof fetch {
    const isUnauthenticatedPopupShown = useRef(false);
    const isUnauthorizedPopupShown = useRef(false);
    const navigate = useNavigate();
    const logOut = useLogOut();

    return async (url: URL | RequestInfo, options?: RequestInit) => {
        try {
            return await authorizedApiFetch(url, options);
        } catch (error: unknown) {
            if (error instanceof UnauthenticatedError) {
                if (!isUnauthenticatedPopupShown.current) {
                    isUnauthenticatedPopupShown.current = true;

                    toastError("Unauthenticated. Please log in.", {
                        onClose: () => { isUnauthenticatedPopupShown.current = false; }
                    });
                }
                navigate(routes.mainPage);
                return new Promise(() => { });

            }
            if (error instanceof UnauthorizedError) {
                if (!isUnauthorizedPopupShown.current) {
                    isUnauthorizedPopupShown.current = true;

                    toastError("Unauthorized.", {
                        onClose: () => { isUnauthorizedPopupShown.current = false; }
                    });
                }
                navigate(routes.mainPage);
                return new Promise(() => { });
            }
            throw error;
        }
    };
}