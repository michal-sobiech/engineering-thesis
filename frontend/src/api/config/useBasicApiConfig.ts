import { Configuration } from "../../GENERATED-api";
import { useBasicApiConfigParameters } from "./useBasicApiConfigParameters";

export function useBasicApiConfig() {
    const basicApiConfigParameters = useBasicApiConfigParameters();
    const basicApiConfig = new Configuration(basicApiConfigParameters);
    return basicApiConfig;
}
