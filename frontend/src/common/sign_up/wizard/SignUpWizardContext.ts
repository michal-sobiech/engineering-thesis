import { ResultAsync } from "neverthrow";
import { createContext } from "react";
import { CreateIndependentEndUserRequest } from "../../../GENERATED-api";
import { VoidCallback } from "../../VoidCallback";

export interface SignUpWizardContextValue {
    incrementStep: VoidCallback<void>,
    email: string,
    setEmail: VoidCallback<string>,
    firstName: string,
    setFirstName: VoidCallback<string>,
    lastName: string
    setLastName: VoidCallback<string>,
    password: string,
    setPassword: VoidCallback<string>,
    logInPageUrl: string,
    createUser: (request: CreateIndependentEndUserRequest) => ResultAsync<void, Error>,
}

export const signUpWizardContext = createContext<SignUpWizardContextValue | null>(null);