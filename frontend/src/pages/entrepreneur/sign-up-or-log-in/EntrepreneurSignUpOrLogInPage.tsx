import { useNavigate } from "react-router";
import { SignUpOrLogInPage, SignUpOrLogInPageProps } from "../../../common/sign-up-or-log-in-page/SingUpOrLogInPage";
import { routes } from "../../../router/routes";

export const EntrepreneurSignUpOrLogInPage = () => {
    const navigate = useNavigate();

    const props: SignUpOrLogInPageProps = {
        roleLabel: "Entrepreneur",
        onLogInButtonClick: () => navigate(routes.entrepreneurLogIn),
        onSignUpButtonClick: () => navigate(routes.entrepreneurSignUp),
    };

    return <SignUpOrLogInPage {...props} />;
};