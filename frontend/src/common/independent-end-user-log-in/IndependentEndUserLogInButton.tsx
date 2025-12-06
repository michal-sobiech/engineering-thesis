import { Result } from "neverthrow";
import { useAuthApi } from "../../api/auth-api";
import { useUsersApi } from "../../api/user-api";
import { useAuth } from "../../auth/useAuth";
import { useContextOrThrow } from "../../hooks/useContextOrThrow";
import { useNavigateWithToastDismiss } from "../../hooks/useNavigateWithToastDismiss";
import { logInCustomer } from "../../services/customer-auth";
import { logInEntrepreneur } from "../../services/entrepreneur-auth";
import { IndependentEndUserLogInOutcome } from "../../services/independent-end-user-auth";
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
            toastError(result.error.message);
            setAuth({ isAuthenticated: false });
            return;
        }

        switch (result.value.status) {
            case "SUCCESS":
                setAuth({
                    isAuthenticated: true,
                    jwtToken: result.value.jwt,
                    userGroup,
                });
                setJwtTokenInLocalStorage(result.value.jwt);
                navigate(landingPageUrl);
                return;
            case "BAD_CREDENTIALS":
                toastError("Bad credentials.");
                setAuth({ isAuthenticated: false });
                return;
            case "WRONG_USER_GROUP":
                toastError("Wrong user group.");
                setAuth({ isAuthenticated: false });
                return;
        }

    }

    return <StandardButton onClick={onClick} >
        Log in
    </StandardButton>
}