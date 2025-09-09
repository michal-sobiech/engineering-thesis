import { Configuration, ConfigurationParameters } from "../../GENERATED-api";

export const basicApiConfigParameters: ConfigurationParameters = {
    basePath: "http://localhost:8080",
    credentials: "omit",
};

export const basicApiConfig = new Configuration(basicApiConfigParameters);
