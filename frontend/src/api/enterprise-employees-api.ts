import { EnterpriseEmployeesApi } from "../GENERATED-api";
import { useAuthorizedApiConfig } from "./config/authorized-api-config";

export function useEnterpriseEmployeesApi() {
    const authorizedApiConfig = useAuthorizedApiConfig();
    return new EnterpriseEmployeesApi(authorizedApiConfig);
}