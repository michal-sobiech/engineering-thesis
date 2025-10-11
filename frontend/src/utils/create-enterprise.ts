import { ResultAsync } from "neverthrow";
import { enterprisesApi } from "../api/enterprises-api";
import { CreateEnterpriseResponse } from "../GENERATED-api";
import { errorErrResultAsyncFromPromise } from "./result";

export function createEnterprise(name: string, description: string, location: string): ResultAsync<CreateEnterpriseResponse, Error> {
    // console.log("Try to create enterprise", auth.value?.jwtToken)
    const promise = enterprisesApi.createEnterprise({ name, description, location, logo: new Blob(), backgroundPhoto: new Blob() });
    return errorErrResultAsyncFromPromise(promise);
}