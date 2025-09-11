import { Configuration, ConfigurationParameters } from "../../GENERATED-api/runtime";
import { useAuthContext } from "../../hooks/useAuthContext";
import { basicApiConfig } from "./basic-api-config";

export const authorizedApiConfigurationParameters: ConfigurationParameters = {
    ...basicApiConfig,
    accessToken: useJwtToken
};

export const auhtorizedApiConfiguration = new Configuration(authorizedApiConfigurationParameters);

function useJwtToken(): string {
    const authContextValue = useAuthContext();
    return authContextValue.auth?.jwtToken || "";
}