import { UnauthorizedError } from "../../utils/error/UnauthorizedError";

export async function authorizedApiFetch(url: URL | RequestInfo, options?: RequestInit) {
    const response = await fetch(url, options);
    if (response.status === 401) {
        throw new UnauthorizedError();
    }
    return response;
}