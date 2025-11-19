import { FC, PropsWithChildren } from "react";
import { ErrorBoundary, FallbackProps } from "react-error-boundary";
import { useNavigate } from "react-router";
import { useLogOut } from "../auth/useLogOut";
import { routes } from "../router/routes";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../utils/error";
import { UnauthorizedError } from "../utils/error/UnauthorizedError";
import { toastError } from "../utils/toast";

export const GlobalErrorHandler: FC<PropsWithChildren> = ({ children }) => {
    const logOut = useLogOut();
    const navigate = useNavigate();

    console.log("SET UP!")

    const fallbackRender = ({ error, resetErrorBoundary }: FallbackProps) => {
        if (error instanceof UnauthorizedError) {
            toastError("Unatuhorized. Please log in.");
            logOut();
        } else {
            toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
        }

        console.log("ERROR CAUGHT");

        navigate(routes.mainPage);
        resetErrorBoundary();
        return children;
    }

    return <ErrorBoundary
        fallbackRender={fallbackRender}>
        {children}
    </ErrorBoundary>
}