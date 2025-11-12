import { AppointmentsApi } from "../GENERATED-api";
import { useAuthorizedApiConfig } from "./config/authorized-api-config";

export function useAppointmentsApi() {
    const authorizedApiConfig = useAuthorizedApiConfig();
    return new AppointmentsApi(authorizedApiConfig);
}