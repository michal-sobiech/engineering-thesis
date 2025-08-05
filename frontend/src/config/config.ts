import Config from "./config.types";
import { loadConfig } from "./loader/config-loader";

export const config: Config = loadConfig();