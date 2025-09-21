import { ResultAsync } from "neverthrow";
import { enterprisesApi } from "../api/enterprises-api";
import { CreateEnterpriseResponse } from "../GENERATED-api";
import { errorErrResultAsyncFromPromise } from "./result";

export function createEnterprise(name: string, description: string, location: string): ResultAsync<CreateEnterpriseResponse, Error> {
    console.log("Try to create enterprise")
    const promise = enterprisesApi.createEnterprise({ enterpriseName: name, description, location });
    return errorErrResultAsyncFromPromise(promise);
}