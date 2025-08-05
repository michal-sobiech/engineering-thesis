import Config from "../config.types";
import { loadBackendUrl } from "./load-backend-url";

function loadConfig(): Config {
    const backendUrl: URL = loadBackendUrl();
    return { backendUrl };
}

export { loadConfig };
