import { ReportApi } from "../GENERATED-api";
import { useAuthorizedApiConfig } from "./config/authorized-api-config";

export function useReportsApi() {
    const authorizedApiConfig = useAuthorizedApiConfig();
    return new ReportApi(authorizedApiConfig);
}