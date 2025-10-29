import { UserApi } from "../GENERATED-api";
import { auhtorizedApiConfiguration } from "./config/authorized-api-config";

export const userApi = new UserApi(auhtorizedApiConfiguration);