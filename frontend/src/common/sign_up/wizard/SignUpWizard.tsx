import { ResultAsync } from "neverthrow";
import { FC, useState } from "react";
import { CreateIndependentEndUserRequest } from "../../../GENERATED-api";
import { SignUpEnterEmailPage } from "../sign_up_enter_email/SignUpEnterEmailPage";
import { SignUpEnterNamePage } from "../sign_up_enter_name/SignUpEnterNamePage";
import SignUpEnterPasswordPage from "../sign_up_enter_password/SignUpEnterPasswordPage";
import SignUpSuccessPage from "../sign_up_success/SignUpSuccessPage";
import { signUpWizardContext } from "./SignUpWizardContext";

export interface SignUpWizardProps {
    logInPageUrl: string;
    createUser: (request: CreateIndependentEndUserRequest) => ResultAsync<void, Error>;
}

export const SignUpWizard: FC<SignUpWizardProps> = ({ logInPageUrl, createUser }) => {
    const [step, setStep] = useState<number>(0);

    const [email, setEmail] = useState<string>("");
    const [firstName, setFirstName] = useState<string>("");
    const [lastName, setLastName] = useState<string>("");
    const [password, setPassword] = useState<string>("");

    const incrementStep = () => setStep(step + 1);

    const contextValue = {
        incrementStep,
        email, setEmail,
        firstName, setFirstName,
        lastName, setLastName,
        password, setPassword,
        logInPageUrl,
        createUser,
    };

    return (
        <signUpWizardContext.Provider value={contextValue} >
            {step === 0 && <SignUpEnterEmailPage />}
            {step === 1 && <SignUpEnterNamePage />}
            {step === 2 && <SignUpEnterPasswordPage />}
            {step === 3 && <SignUpSuccessPage />}
        </signUpWizardContext.Provider>
    );

};
