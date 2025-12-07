import { AdyenApi } from "../GENERATED-api";
import { useAuthorizedApiConfig } from "./config/authorized-api-config";

export function useAdyenApi() {
    const authorizedApiConfig = useAuthorizedApiConfig();
    return new AdyenApi(authorizedApiConfig);
}