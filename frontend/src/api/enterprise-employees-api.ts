import { EnterpriseEmployeesApi } from "../GENERATED-api";
import { auhtorizedApiConfiguration as authorizedApiConfiguration } from "./config/authorized-api-config";

export const enterpriseEmployeesApi = new EnterpriseEmployeesApi(authorizedApiConfiguration);