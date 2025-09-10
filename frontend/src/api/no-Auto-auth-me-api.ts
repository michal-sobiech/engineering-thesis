import { MeApi } from "../GENERATED-api";
import { basicApiConfig } from "./config/basic-api-config";

export const noAutoAuthMeApi = new MeApi(basicApiConfig);