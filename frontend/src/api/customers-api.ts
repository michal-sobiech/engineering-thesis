import { CustomersApi } from "../GENERATED-api";
import { useAuthorizedApiConfig } from "./config/authorized-api-config";

export function useCustomersApi() {
    const authorizedApiConfig = useAuthorizedApiConfig();
    return new CustomersApi(authorizedApiConfig);
}