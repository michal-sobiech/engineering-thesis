import { CustomersApi } from "../GENERATED-api";
import { auhtorizedApiConfiguration } from "./config/authorized-api-config";

export const customersApi = new CustomersApi(auhtorizedApiConfiguration);