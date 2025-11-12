import { ResultAsync } from "neverthrow";
import { EnterprisesApi } from "../../GENERATED-api";
import { GetEnterpriseServicesResponseItem } from "../../GENERATED-api/models/GetEnterpriseServicesResponseItem";
import { errorErrResultAsyncFromPromise } from "../../utils/result";

export function fetchServices(enterpriseId: number, enterprisesApi: EnterprisesApi): ResultAsync<GetEnterpriseServicesResponseItem[], Error> {
    const promise = enterprisesApi.getEnterpriseServices(enterpriseId);
    return errorErrResultAsyncFromPromise(promise);
}