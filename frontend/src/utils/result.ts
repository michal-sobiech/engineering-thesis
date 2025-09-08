import { errAsync, fromPromise as neverthrowFromPromise, okAsync, Result, ResultAsync } from "neverthrow";

export function fromPromise<T>(promise: PromiseLike<T>): ResultAsync<T, Error> {
    return neverthrowFromPromise(promise, (e) => new Error(String(e)));
}

export function fromResult<T, E>(result: Result<T, E>): ResultAsync<T, E> {
    return result.isOk()
        ? okAsync(result.value)
        : errAsync(result.error);
}