import { ok, ResultAsync } from "neverthrow";
import { IndependentEndUsersApi } from "../GENERATED-api";
import { matchesEmailPattern } from "../utils/email";
import { errorErrResultAsyncFromPromise } from "../utils/result";

export function isEmailValidAndAvailable(email: string, independentEndUsersApi: IndependentEndUsersApi): ResultAsync<boolean, Error> {
    return ok(matchesEmailPattern(email))
        .asyncAndThen(() => isEmailAvailable(email, independentEndUsersApi));
}

export function isEmailAvailable(email: string, independentEndUsersApi: IndependentEndUsersApi): ResultAsync<boolean, Error> {
    const promise = independentEndUsersApi.checkIndependentEndUserEmailExists(email);
    return errorErrResultAsyncFromPromise(promise)
        .map(response => !response.isExisting);
} 