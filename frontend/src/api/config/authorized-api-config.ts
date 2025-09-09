import { authContext } from "../../auth-context/storage";
import { Configuration, ConfigurationParameters } from "../../GENERATED-api/runtime";
import { basicApiConfig } from "./basic-api-config";

export const authorizedApiConfigurationParameters: ConfigurationParameters = {
    ...basicApiConfig,
    accessToken: getJwtToken
};

export const auhtorizedApiConfiguration = new Configuration(authorizedApiConfigurationParameters);

function getJwtToken(): string {
    return authContext?.jwtToken || "";
}