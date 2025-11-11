import { useNavigate } from "react-router";
import { useContextOrThrow } from "../../../hooks/useContextOrThrow";
import { StandardButton } from "../../StandardButton";
import { signUpWizardContext } from "../wizard/SignUpWizardContext";

const SignUpSuccessButton = () => {
    const navigate = useNavigate();

    const { logInPageUrl } = useContextOrThrow(signUpWizardContext);

    const onClick = async () => {
        navigate(logInPageUrl);
    }

    return <StandardButton onClick={onClick}>
        Log in
    </StandardButton>;
}

export default SignUpSuccessButton;