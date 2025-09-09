import { err, errAsync, fromPromise as neverthrowFromPromise, ok, okAsync, Result, ResultAsync } from "neverthrow";

export function createDefaultResultAsyncfromPromise<T>(promise: PromiseLike<T>): ResultAsync<T, string> {
    return neverthrowFromPromise(promise, () => "Unexpected error. Please try again later.");
}

export function fromResult<T, E>(result: Result<T, E>): ResultAsync<T, E> {
    return result.isOk()
        ? okAsync(result.value)
        : errAsync(result.error);
}

export function fromNullable<T extends NonNullable<unknown>>(value: T | null | undefined): Result<T, string> {
    if (value) {
        return ok(value);
    } else if (value === null) {
        return err("Value is null");
    } else {
        return err("Value is undefined");
    }
}