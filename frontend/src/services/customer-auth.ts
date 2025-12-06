import { ResultAsync } from "neverthrow";
import { AuthApi, UserApi } from "../GENERATED-api";
import { errorErrResultAsyncFromPromise } from "../utils/result";
import { IndependentEndUserLogInOutcome, logInIndependentEndUserWithGroupResolve } from "./independent-end-user-auth";

export function logInCustomer(email: string, password: string, authApi: AuthApi, userApi: UserApi): ResultAsync<IndependentEndUserLogInOutcome, Error> {
    const promise = logInIndependentEndUserWithGroupResolve(email, password, "CUSTOMER", authApi, userApi);
    return errorErrResultAsyncFromPromise(promise);
}