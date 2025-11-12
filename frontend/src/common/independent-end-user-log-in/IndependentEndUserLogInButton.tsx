import { Result } from "neverthrow";
import { useContextOrThrow } from "../../hooks/useContextOrThrow";
import { useNavigateWithToastDismiss } from "../../hooks/useNavigateWithToastDismiss";
import { logInCustomer } from "../../services/customer-auth";
import { logInEntrepreneur } from "../../services/entrepreneur-auth";
import { IndependentEndUserLogInStatus } from "../../services/independent-end-user-auth";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../utils/error";
import { toastError } from "../../utils/toast";
import { StandardButton } from "../StandardButton";
import { logInContext } from "./IndependentEndUserLogInContext";

export const IndependentEndUserLogInButton = () => {
    const { email, password, userGroup, landingPageUrl } = useContextOrThrow(logInContext);
    const navigate = useNavigateWithToastDismiss();

    const onClick = async () => {
        var result: Result<IndependentEndUserLogInStatus, Error>;
        if (userGroup === "CUSTOMER") {
            result = await logInCustomer(email, password);
        } else {
            result = await logInEntrepreneur(email, password);
        }

        if (result.isErr()) {
            toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
            return;
        }

        if (result.value === "SUCCESS") {
            throw navigate(landingPageUrl);
        } else {
            toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
            return;
        }

    }

    return <StandardButton onClick={onClick} >
        Log in
    </StandardButton>
}