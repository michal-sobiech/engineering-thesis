import { useState } from "react";

export const EmployeeCreationWizard = () => {
    const [step, setStep] = useState<number>(0);

    const [username, setUsername] = useState<string>("");
    const [firstName, setFirstName] = useState<string>("");
    const [lastName, setLastName] = useState<string>("");
    const [password, setPassword] = useState<string>("");

    const incrementStep = () => setStep(step + 1);

    const contextValue = {
        incrementStep,
        username, setUsername,
        firstName, setFirstName,
        lastName, setLastName,
        password, setPassword
    };

    return (
        <EmployeeCreationWizard.Provider value={contextValue} >
            {step === 0 && <SignUpEnterEmailPage />}
            {step === 1 && <SignUpEnterNamePage />}
            {step === 2 && <SignUpEnterPasswordPage />}
            {step === 3 && <SignUpSuccessPage />}
        </EmployeeCreationWizard.Provider>
    );

};
