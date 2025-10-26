import { EnterpriseServicesApi } from "../GENERATED-api";
import { auhtorizedApiConfiguration } from "./config/authorized-api-config";

export const servicesApi = new EnterpriseServicesApi(auhtorizedApiConfiguration);