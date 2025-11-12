import { IndependentEndUsersApi } from "../GENERATED-api";
import { useAuthorizedApiConfig } from "./config/authorized-api-config";

export function useIndependentEndUsersApi() {
    const authorizedApiConfig = useAuthorizedApiConfig();
    return new IndependentEndUsersApi(authorizedApiConfig);
}