import { useAuth } from "../../auth/useAuth";
import { ConfigurationParameters } from "../../GENERATED-api";
import { useAuthorizedApiFetch } from "./useAuthorizedApiFetch";

export function useAuthorizedApiConfigParameters() {
    const { auth } = useAuth();
    const authorizedApiFetch = useAuthorizedApiFetch();

    const jwt = auth.isAuthenticated ? auth.jwtToken : "";

    const authorizedApiConfigurationParameters: ConfigurationParameters = {
        // ...basicApiConfigParameters,
        basePath: "http://localhost:8080",
        accessToken: jwt,
        fetchApi: authorizedApiFetch,
    };

    return authorizedApiConfigurationParameters;

}