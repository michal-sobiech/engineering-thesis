import { ResultAsync } from "neverthrow";
import { enterprisesApi } from "../../api/enterprises-api";
import { GetEnterpriseServicesResponseItem } from "../../GENERATED-api/models/GetEnterpriseServicesResponseItem";
import { errorErrResultAsyncFromPromise } from "../../utils/result";

export function fetchServices(enterpriseId: number): ResultAsync<GetEnterpriseServicesResponseItem[], Error> {
    const promise = enterprisesApi.getEnterpriseServices(enterpriseId);
    return errorErrResultAsyncFromPromise(promise);
}