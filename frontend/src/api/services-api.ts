import { EnterpriseServicesApi } from "../GENERATED-api";
import { useAuthorizedApiConfig } from "./config/authorized-api-config";

export function useServicesApi() {
    const authorizedApiConfig = useAuthorizedApiConfig();
    return new EnterpriseServicesApi(authorizedApiConfig);
}