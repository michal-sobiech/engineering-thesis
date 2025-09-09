import { EntrepreneursApi } from "../GENERATED-api";
import { auhtorizedApiConfiguration } from "./config/authorized-api-config";

export const entrepreneursApi = new EntrepreneursApi(auhtorizedApiConfiguration);