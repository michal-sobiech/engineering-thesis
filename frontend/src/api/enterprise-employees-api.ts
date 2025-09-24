import { EnterpriseEmployeesApi } from "../GENERATED-api";
import { auhtorizedApiConfiguration } from "./config/authorized-api-config";

export const enterpriseEmployeesApi = new EnterpriseEmployeesApi(auhtorizedApiConfiguration);