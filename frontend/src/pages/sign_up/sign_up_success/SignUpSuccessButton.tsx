import { useNavigate } from "react-router";
import { StandardButton } from "../../../common/StandardButton";
import { routes } from "../../../router/routes";

const SignUpSuccessButton = () => {
    const navigate = useNavigate();

    const onClick = async () => {
        console.log("eeee");
        navigate(routes.LOG_IN);
    }

    return <StandardButton onClick={onClick}>
        Log in
    </StandardButton>;
}

export default SignUpSuccessButton;