import { ResultAsync } from "neverthrow";
import { IndependentEndUserLogInStatus, logInIndependentEndUser } from "./independent-end-user-auth";

export function logInCustomer(email: string, password: string): ResultAsync<IndependentEndUserLogInStatus, Error> {
    return logInIndependentEndUser(email, password, "CUSTOMER");
}