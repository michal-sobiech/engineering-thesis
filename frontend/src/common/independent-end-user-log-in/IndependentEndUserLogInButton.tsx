import { Result } from "neverthrow";
import { useAuthApi } from "../../api/auth-api";
import { useUsersApi } from "../../api/user-api";
import { useAuth } from "../../auth/useAuth";
import { useContextOrThrow } from "../../hooks/useContextOrThrow";
import { useNavigateWithToastDismiss } from "../../hooks/useNavigateWithToastDismiss";
import { logInCustomer } from "../../services/customer-auth";
import { logInEntrepreneur } from "../../services/entrepreneur-auth";
import { IndependentEndUserLogInOutcome } from "../../services/independent-end-user-auth";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../utils/error";
import { toastError } from "../../utils/toast";
import { StandardButton } from "../StandardButton";
import { setJwtTokenInLocalStorage } from "../local-storage";
import { logInContext } from "./IndependentEndUserLogInContext";

export const IndependentEndUserLogInButton = () => {
    const authApi = useAuthApi();
    const userApi = useUsersApi();
    const { setAuth } = useAuth();
    const { email, password, userGroup, landingPageUrl } = useContextOrThrow(logInContext);
    const navigate = useNavigateWithToastDismiss();

    const onClick = async () => {
        var result: Result<IndependentEndUserLogInOutcome, Error>;
        if (userGroup === "CUSTOMER") {
            result = await logInCustomer(email, password, authApi, userApi);
        } else {
            result = await logInEntrepreneur(email, password, authApi, userApi);
        }

        if (result.isErr()) {
            toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
            return;
        }

        if (result.value.status === "SUCCESS") {
            setAuth({
                isAuthenticated: true,
                jwtToken: result.value.jwt,
                userGroup,
            });
            setJwtTokenInLocalStorage(result.value.jwt);
            navigate(landingPageUrl);
            return;
        } else {
            toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
            return;
        }

    }

    return <StandardButton onClick={onClick} >
        Log in
    </StandardButton>
}