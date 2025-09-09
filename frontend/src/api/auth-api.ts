import { AuthApi } from "../GENERATED-api";
import { basicApiConfig } from "./config/basic-api-config";

export const authApi = new AuthApi(basicApiConfig);