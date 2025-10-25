import { useNavigate } from "react-router";
import { SignUpOrLogInPage, SignUpOrLogInPageProps } from "../../common/sign-up-or-log-in-page/SingUpOrLogInPage";
import { routes } from "../../router/routes";

export const CustomerSignUpOrLogIn = () => {
    const navigate = useNavigate();

    const props: SignUpOrLogInPageProps = {
        roleLabel: "Customer",
        onLogInButtonClick: () => navigate(routes.customerLogIn),
        onSignUpButtonClick: () => navigate(routes.customerSignUp),
    };

    return <SignUpOrLogInPage {...props} />;
};