import { UserApi } from "../GENERATED-api";
import { useAuthorizedApiConfig } from "./config/authorized-api-config";

export function useUsersApi() {
    const authorizedApiConfig = useAuthorizedApiConfig();
    return new UserApi(authorizedApiConfig);
}