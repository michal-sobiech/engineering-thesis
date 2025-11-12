import { AuthApi } from "../GENERATED-api";
import { useBasicApiConfig } from "./config/useBasicApiConfig";

export function useIndependentEndUsersApi() {
    const basicApiConfig = useBasicApiConfig();
    return new AuthApi(basicApiConfig);
}