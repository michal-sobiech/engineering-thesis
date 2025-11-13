import { ResultAsync } from "neverthrow";
import { AuthApi, UserApi } from "../GENERATED-api";
import { IndependentEndUserLogInOutcome, logInIndependentEndUser } from "./independent-end-user-auth";

export function logInEntrepreneur(email: string, password: string, authApi: AuthApi, userApi: UserApi): ResultAsync<IndependentEndUserLogInOutcome, Error> {
    return logInIndependentEndUser(email, password, "ENTREPRENEUR", authApi, userApi);
}