import { useNavigate } from "react-router";
import { StandardButton } from "../../../common/StandardButton";
import { routes } from "../../../router/routes";

const SignUpSuccessButton = () => {
    const navigate = useNavigate();

    const onClick = async () => {
        navigate(routes.logIn);
    }

    return <StandardButton onClick={onClick}>
        Log in
    </StandardButton>;
}

export default SignUpSuccessButton;