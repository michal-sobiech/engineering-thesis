import { ConfigurationParameters } from "../../GENERATED-api";

export function useBasicApiConfigParameters() {
    const basicApiConfigParameters: ConfigurationParameters = {
        basePath: "http://localhost:8080",
        credentials: "omit",
    };

    return basicApiConfigParameters;
}
