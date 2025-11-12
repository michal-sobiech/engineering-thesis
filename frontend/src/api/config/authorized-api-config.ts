import { Configuration } from "../../GENERATED-api/runtime";
import { useAuthorizedApiConfigParameters } from "./useAuthorizedApiConfigParameters";

export function useAuthorizedApiConfig() {
    const authorizedApiConfigurationParameters = useAuthorizedApiConfigParameters();
    const authorizedApiConfiguration = new Configuration(authorizedApiConfigurationParameters);
    return authorizedApiConfiguration;
}
