import { useNavigate } from "react-router";
import { useLogOut } from "../../auth/useLogOut";
import { routes } from "../../router/routes";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../utils/error";
import { UnauthorizedError } from "../../utils/error/UnauthorizedError";
import { toastError } from "../../utils/toast";
import { authorizedApiFetch } from "./authorized-api-fetch";

export function useAuthorizedApiFetch(): typeof fetch {
    const navigate = useNavigate();
    const logOut = useLogOut();

    return async (url: URL | RequestInfo, options?: RequestInit) => {
        console.log("LOGGEDD")
        try {
            return await authorizedApiFetch(url, options);
        } catch (error: unknown) {
            if (error instanceof UnauthorizedError) {
                toastError("Unauthorized. Please log in.");
                logOut();
            } else {
                toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
            }
            throw navigate(routes.mainPage);
        }
    };
}