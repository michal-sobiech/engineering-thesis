import { useAuth } from "../../auth/useAuth";
import { ConfigurationParameters } from "../../GENERATED-api";
import { authorizedApiFetch } from "./authorized-api-fetch";

export function useAuthorizedApiConfigParameters() {
    const { auth } = useAuth();

    const jwt = auth.isAuthenticated ? auth.jwtToken : "";

    const authorizedApiConfigurationParameters: ConfigurationParameters = {
        // ...basicApiConfigParameters,
        basePath: "http://localhost:8080",
        accessToken: jwt,
        fetchApi: authorizedApiFetch,
    };

    return authorizedApiConfigurationParameters;

}