import { EntrepreneursApi } from "../GENERATED-api";
import { useAuthorizedApiConfig } from "./config/authorized-api-config";

export function useEntrepreneursApi() {
    const authorizedApiConfig = useAuthorizedApiConfig();
    return new EntrepreneursApi(authorizedApiConfig);
}