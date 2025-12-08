import { useState } from "react";
import { useEnterpriseIdFromLoader } from "../../../../common/loader/enterprise-id-loader";
import { EmployeeCreationEnterNamePage } from "../enter_name/EmployeeCreationEnterNamePage";
import { EmployeeCreationEnterPasswordPage } from "../enter_password/EmployeeCreationEnterPasswordPage";
import { EmployeeCreationEnterUsernamePage } from "../enter_username/EmployeeCreationEnterUsernamePage";
import { EmployeeCreationSuccessPage } from "../success/EmployeeCreationSuccessPage";
import { employeeCreationWizardContext } from "./EmployeeCreationWizardContext";

export const EmployeeCreationWizard = () => {
    const [step, setStep] = useState<number>(0);
    const enterpriseId = useEnterpriseIdFromLoader();

    // TODO not load if user has no access to enterprise with given id

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
        <employeeCreationWizardContext.Provider value={contextValue} >
            {step === 0 && <EmployeeCreationEnterUsernamePage />}
            {step === 1 && <EmployeeCreationEnterNamePage />}
            {step === 2 && <EmployeeCreationEnterPasswordPage />}
            {step === 3 && <EmployeeCreationSuccessPage />}
        </employeeCreationWizardContext.Provider>
    );

};
