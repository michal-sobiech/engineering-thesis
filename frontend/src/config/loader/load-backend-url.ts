import { assertDefined } from "../../utils/AssertUtils";

function loadBackendUrl(): URL {
    const backendUrl: string | undefined = process.env.REACT_APP_BACKEND_URL;
    const nonNullUrl: string = assertDefined(backendUrl);
    return new URL(nonNullUrl);
}

export { loadBackendUrl };
