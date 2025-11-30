import { LocalDate } from "@js-joda/core";
import { useState } from "react";
import { useNavigate } from "react-router";
import { useEntrepreneursApi } from "../../api/entrepreneurs-api";
import { SignUpSuccessPage } from "../../common/sign-up-page/sign_up_success/SignUpSuccessPage";
import { SignUpEnterEmailPage } from "../../common/sign-up-page/SignUpEnterEmailPage";
import { SignUpEnterNamePage } from "../../common/sign-up-page/SignUpEnterNamePage";
import { SignUpEnterPasswordPage } from "../../common/sign-up-page/SignUpEnterPasswordPage";
import { routes } from "../../router/routes";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../utils/error";
import { errorErrResultAsyncFromPromise } from "../../utils/result";
import { toastError } from "../../utils/toast";

export const EntrepreneurSignUpWizard = () => {
    const entrepreneursApi = useEntrepreneursApi();
    const logInPageUrl = routes.entrepreneurLogIn;

    const navigate = useNavigate();

    const [step, setStep] = useState<number>(0);
    const incrementStep = () => setStep(step + 1);

    const [email, setEmail] = useState<string>("");
    const [firstName, setFirstName] = useState<string>("");
    const [lastName, setLastName] = useState<string>("");
    const [password, setPassword] = useState<string>("");

    const [dateOfBirth, setDateOfBirth] = useState<LocalDate | null>(null);
    const [country, setCountry] = useState<string>("");
    const [city, setCity] = useState<string>("");
    const [street, setStreet] = useState<string>("");
    const [postalCode, setPostalCode] = useState<string>("");
    const [iban, setIban] = useState<string>("");

    const accountCreationSubmit = async () => {
        const promise = entrepreneursApi.createEntrepreneur({
            firstName,
            lastName,
            email,
            password,
        });
        const result = await errorErrResultAsyncFromPromise(promise);
        if (result.isErr()) {
            toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
            return;
        }
        incrementStep();
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
            submit={accountCreationSubmit}
            password={password}
            setPassword={setPassword} />;
    } else if (step === 3) {
        return <SignUpSuccessPage logInPageUrl={logInPageUrl} />;
    } else {
        return null;
    }

};
