import { UnauthenticatedError } from "../../utils/error/UnauthenticatedError";
import { UnauthorizedError } from "../../utils/error/UnauthorizedError";

export async function authorizedApiFetch(url: URL | RequestInfo, options?: RequestInit): Promise<Response> {
    const response = await fetch(url, options);
    if (response.status === 401) {
        throw new UnauthenticatedError();
    }
    if (response.status === 403) {
        throw new UnauthorizedError();
    }
    return response;
}