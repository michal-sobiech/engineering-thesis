import { authCell } from "../../auth/AuthProvider";
import { Configuration, ConfigurationParameters } from "../../GENERATED-api/runtime";


export const authorizedApiConfigurationParameters: ConfigurationParameters = {
    // ...basicApiConfigParameters,
    // accessToken: () => getJwtToken()
    // accessToken: "aaaaaaaaaaaaaaaaaaaaa"
    basePath: "http://localhost:8080",
    // accessToken: () => getJwtToken()
    accessToken: "aaaaaaaaaaaaaaaaaaaaa"
};

export const auhtorizedApiConfiguration = new Configuration(authorizedApiConfigurationParameters);

function getJwtToken(): string {
    console.log("123", authCell.value?.jwtToken || "");
    return authCell.value?.jwtToken || "";
}