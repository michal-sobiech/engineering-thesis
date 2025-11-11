import { RegularAdminsApi } from "../GENERATED-api";
import { auhtorizedApiConfiguration } from "./config/authorized-api-config";

export const regularAdminsApi = new RegularAdminsApi(auhtorizedApiConfiguration);