import { err, errAsync, fromPromise as neverthrowFromPromise, ok, okAsync, Result, ResultAsync } from "neverthrow";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "./error";

export function defaultStringErrResultAsyncFromPromise<T>(promise: PromiseLike<T>): ResultAsync<T, string> {
    return neverthrowFromPromise(promise, () => DEFAULT_ERROR_MESSAGE_FOR_USER);
}

export function stringErrResultAsyncFromPromise<T>(promise: PromiseLike<T>): ResultAsync<T, string> {
    return neverthrowFromPromise(promise, (e) => String(e));
}

export function errorErrResultAsyncFromPromise<T>(promise: PromiseLike<T>): ResultAsync<T, Error> {
    return stringErrResultAsyncFromPromise(promise).mapErr((e) => new Error(e));
}

export function fromResult<T, E>(result: Result<T, E>): ResultAsync<T, E> {
    return result.isOk()
        ? okAsync(result.value)
        : errAsync(result.error);
}

export function assertDefined<T extends NonNullable<unknown>>(value: T | null | undefined): Result<T, Error> {
    if (value) {
        return ok(value);
    } else if (value === null) {
        return err(new Error("Value is null"));
    } else {
        return err(new Error("Value is undefined"));
    }
}

export function promiseResultToAsyncResult<T, E>(
    promise: Promise<Result<T, E>>,
    errorMapper: (error: unknown) => E,
): ResultAsync<T, E> {
    return ResultAsync.fromPromise(promise, errorMapper)
        .andThen(nestedResult => fromResult(nestedResult));
}

export function promiseResultToErrorAsyncResult<T>(promise: Promise<Result<T, Error>>): ResultAsync<T, Error> {
    const errorMapper = (error: unknown) => new Error(String(error));
    return promiseResultToAsyncResult(promise, errorMapper);
}