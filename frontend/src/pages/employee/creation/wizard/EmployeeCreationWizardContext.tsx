import { createContext } from "react";
import { VoidCallback } from "../../../../common/VoidCallback";

export interface EmployeeCreationWizardContextValue {
    incrementStep: VoidCallback<void>,
    username: string,
    setUsername: VoidCallback<string>,
    firstName: string,
    setFirstName: VoidCallback<string>,
    lastName: string
    setLastName: VoidCallback<string>,
    password: string,
    setPassword: VoidCallback<string>,
}

export const employeeCreationWizardContext = createContext<EmployeeCreationWizardContextValue | null>(null);