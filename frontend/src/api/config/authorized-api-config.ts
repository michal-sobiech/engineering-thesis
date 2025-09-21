import { Configuration, ConfigurationParameters } from "../../GENERATED-api/runtime";
import { useAuthContext } from "../../hooks/useAuthContext";
import { basicApiConfig } from "./basic-api-config";

console.log("authorized-api-config.ts")

const authContextValue = useAuthContext();

export const authorizedApiConfigurationParameters: ConfigurationParameters = {
    ...basicApiConfig,
    accessToken: useJwtToken,
};

export const auhtorizedApiConfiguration = new Configuration(authorizedApiConfigurationParameters);

function useJwtToken(): string {
    console.log("useJwtToken", authContextValue);
    return authContextValue.auth?.jwtToken || "";
}