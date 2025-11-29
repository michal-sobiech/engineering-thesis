import { useState } from "react";
import { useNavigate } from "react-router";
import { useCustomersApi } from "../../api/customers-api";
import { SignUpSuccessPage } from "../../common/sign-up-page/sign_up_success/SignUpSuccessPage";
import { SignUpEnterEmailPage } from "../../common/sign-up-page/SignUpEnterEmailPage";
import { SignUpEnterNamePage } from "../../common/sign-up-page/SignUpEnterNamePage";
import { SignUpEnterPasswordPage } from "../../common/sign-up-page/SignUpEnterPasswordPage";
import { routes } from "../../router/routes";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../utils/error";
import { errorErrResultAsyncFromPromise } from "../../utils/result";
import { toastError } from "../../utils/toast";

export const CustomerSignUpWizard = () => {
    const customersApi = useCustomersApi();
    const logInPageUrl = routes.customerLogIn;

    const navigate = useNavigate();

    const [step, setStep] = useState<number>(0);
    const incrementStep = () => setStep(step + 1);

    const [email, setEmail] = useState<string>("");
    const [firstName, setFirstName] = useState<string>("");
    const [lastName, setLastName] = useState<string>("");
    const [password, setPassword] = useState<string>("");

    const enterPasswordPageSubmit = async () => {
        const promise = customersApi.createCustomer({
            firstName,
            lastName,
            email,
            password
        });
        const result = await errorErrResultAsyncFromPromise(promise);
        if (result.isErr()) {
            toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
            return;
        }
        navigate(logInPageUrl);
    }

    if (step === 0) {
        return <SignUpEnterEmailPage
            submit={incrementStep}
            email={email}
            setEmail={setEmail} />;
    } else if (step === 1) {
        return <SignUpEnterNamePage
            submit={incrementStep}
            firstName={firstName}
            setFirstName={setFirstName}
            lastName={lastName}
            setLastName={setLastName} />
    } else if (step === 2) {
        return <SignUpEnterPasswordPage
            submit={enterPasswordPageSubmit}
            password={password}
            setPassword={setPassword} />;
    } else if (step === 3) {
        return <SignUpSuccessPage logInPageUrl={logInPageUrl} />;
    } else {
        return null;
    }

};
