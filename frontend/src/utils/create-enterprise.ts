import { ResultAsync } from "neverthrow";
import { enterprisesApi } from "../api/enterprises-api";
import { errorErrResultAsyncFromPromise } from "./result";

export function createEnterprise(name: string, description: string, location: string): ResultAsync<void, Error> {
    const promise = enterprisesApi.createEnterprise({ enterpriseName: name, description, location });
    return errorErrResultAsyncFromPromise(promise)
}