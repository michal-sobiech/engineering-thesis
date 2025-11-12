import { EnterprisesApi } from "../GENERATED-api";
import { useAuthorizedApiConfig } from "./config/authorized-api-config";

export function useEnterprisesApi() {
    const authorizedApiConfig = useAuthorizedApiConfig();
    return new EnterprisesApi(authorizedApiConfig);
}