import { IndependentEndUsersApi } from "../GENERATED-api";
import { auhtorizedApiConfiguration } from "./config/authorized-api-config";

export const independentEndUsersApi = new IndependentEndUsersApi(auhtorizedApiConfiguration)