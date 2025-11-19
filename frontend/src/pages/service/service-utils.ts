import { ResultAsync } from "neverthrow";
import { EnterprisesApi, GetEnterpriseService200Response } from "../../GENERATED-api";
import { errorErrResultAsyncFromPromise } from "../../utils/result";

export function fetchServices(enterpriseId: number, enterprisesApi: EnterprisesApi): ResultAsync<GetEnterpriseService200Response[], Error> {
    const promise = enterprisesApi.getEnterpriseServices(enterpriseId);
    return errorErrResultAsyncFromPromise(promise);
}