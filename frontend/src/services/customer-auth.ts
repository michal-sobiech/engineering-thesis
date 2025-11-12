import { ResultAsync } from "neverthrow";
import { AuthApi, UserApi } from "../GENERATED-api";
import { IndependentEndUserLogInStatus, logInIndependentEndUser } from "./independent-end-user-auth";

export function logInCustomer(email: string, password: string, authApi: AuthApi, userApi: UserApi): ResultAsync<IndependentEndUserLogInStatus, Error> {
    return logInIndependentEndUser(email, password, "CUSTOMER", authApi, userApi);
}