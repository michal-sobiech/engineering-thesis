import { useAuth } from "../auth/useAuth";
import { Configuration, ConfigurationParameters, UserApi } from "../GENERATED-api";

export function useStdFetchUserApi(): UserApi {
    const { auth } = useAuth();

    const jwt = auth.isAuthenticated ? auth.jwtToken : "";

    const configParams: ConfigurationParameters = {
        basePath: "http://localhost:8080",
        accessToken: jwt,
    };
    const config = new Configuration(configParams);
    return new UserApi(config);
}