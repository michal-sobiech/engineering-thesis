import { errAsync, fromPromise as neverthrowFromPromise, okAsync, Result, ResultAsync } from "neverthrow";

export function createDefaultResultfromPromise<T>(promise: PromiseLike<T>): ResultAsync<T, string> {
    return neverthrowFromPromise(promise, () => "Unexpected error. Please try again later.");
}

export function fromResult<T, E>(result: Result<T, E>): ResultAsync<T, E> {
    return result.isOk()
        ? okAsync(result.value)
        : errAsync(result.error);
}