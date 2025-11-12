import { AuthApi } from "../GENERATED-api";
import { useBasicApiConfig } from "./config/useBasicApiConfig";

export function useAuthApi() {
    const basicApiConfig = useBasicApiConfig();
    return new AuthApi(basicApiConfig);
}