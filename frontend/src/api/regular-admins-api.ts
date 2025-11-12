import { RegularAdminsApi } from "../GENERATED-api";
import { useAuthorizedApiConfig } from "./config/authorized-api-config";

export function useRegularAdminsApi() {
    const authorizedApiConfig = useAuthorizedApiConfig();
    return new RegularAdminsApi(authorizedApiConfig);
}