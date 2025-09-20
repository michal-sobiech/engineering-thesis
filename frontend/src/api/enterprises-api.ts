import { EnterprisesApi } from "../GENERATED-api";
import { auhtorizedApiConfiguration } from "./config/authorized-api-config";

export const enterprisesApi = new EnterprisesApi(auhtorizedApiConfiguration);