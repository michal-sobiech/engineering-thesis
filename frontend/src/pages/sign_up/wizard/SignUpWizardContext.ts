import { createContext } from "react";
import { VoidCallback } from "../../../common/VoidCallback";

export interface SignUpWizardContextData {
    incrementStep: VoidCallback<void>,
    email: string,
    setEmail: VoidCallback<string>,
    firstName: string,
    setFirstName: VoidCallback<string>,
    lastName: string
    setLastName: VoidCallback<string>,
    password: string,
    setPassword: VoidCallback<string>,
}

export const signUpWizardContext = createContext<SignUpWizardContextData | null>(null);