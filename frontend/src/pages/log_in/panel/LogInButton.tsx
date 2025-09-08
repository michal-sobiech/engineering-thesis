import { useState } from "react";
import { StandardButton } from "../../../common/StandardButton";
import { logIn } from "../../../utils/log-in";
import { toastError } from "../../../utils/toast";
import { useContextOrThrow } from "../../../utils/useContextOrThrow";
import { logInContext } from "../LogInContext";

export const LogInButton = () => {
    const [disabled, setDisabled] = useState<boolean>(false);

    const { email, password } = useContextOrThrow(logInContext);

    const onClick = async () => {
        setDisabled(true);
        try {
            await logIn(email, password);
        } catch (error) {
            toastError("Couldn't log in. Try again later.");
        } finally {
            setDisabled(false);
        }
    }

    return <StandardButton onClick={onClick} disabled={disabled}>
        Log in
    </StandardButton>
}