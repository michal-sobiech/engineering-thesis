import { ResultAsync } from "neverthrow";
import { IndependentEndUserLogInStatus, logInIndependentEndUser } from "./independent-end-user-auth";

export function logInEntrepreneur(email: string, password: string): ResultAsync<IndependentEndUserLogInStatus, Error> {
    return logInIndependentEndUser(email, password, "ENTREPRENEUR");
}