import { useRef } from "react";
import { useNavigate } from "react-router";
import { useLogOut } from "../../auth/useLogOut";
import { routes } from "../../router/routes";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../utils/error";
import { UnauthorizedError } from "../../utils/error/UnauthorizedError";
import { toastError } from "../../utils/toast";
import { authorizedApiFetch } from "./authorized-api-fetch";

export function useAuthorizedApiFetch(): typeof fetch {
    const isUnauthorizedPopupShown = useRef(false);
    const isDefaultErrorPopupShown = useRef(false);
    const navigate = useNavigate();
    const logOut = useLogOut();

    return async (url: URL | RequestInfo, options?: RequestInit) => {
        try {
            return await authorizedApiFetch(url, options);
        } catch (error: unknown) {
            if (error instanceof UnauthorizedError) {
                if (!isUnauthorizedPopupShown.current) {
                    isUnauthorizedPopupShown.current = true;

                    toastError("Unauthorized. Please log in.", {
                        onClose: () => { isUnauthorizedPopupShown.current = false; }
                    });
                }
                logOut();
            } else {
                if (!isDefaultErrorPopupShown.current) {
                    isDefaultErrorPopupShown.current = true;

                    toastError(DEFAULT_ERROR_MESSAGE_FOR_USER, {
                        onClose: () => { isDefaultErrorPopupShown.current = false; }
                    });
                }
            }
            throw navigate(routes.mainPage);
        }
    };
}