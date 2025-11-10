import { ReportApi } from "../GENERATED-api";
import { auhtorizedApiConfiguration } from "./config/authorized-api-config";

export const reportsApi = new ReportApi(auhtorizedApiConfiguration);