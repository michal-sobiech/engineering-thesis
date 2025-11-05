import { AppointmentsApi } from "../GENERATED-api";
import { auhtorizedApiConfiguration } from "./config/authorized-api-config";

export const appointmentsApi = new AppointmentsApi(auhtorizedApiConfiguration);