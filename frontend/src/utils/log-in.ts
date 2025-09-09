import { authApi } from "../api/auth-api";
import { updateStorage } from "../auth-context/storage";

export async function logInAndSetJwtToken(email: string, password: string): Promise<void> {
    const { accessToken } = await authApi.logInIndependentEndUser({ email, password });
    updateStorage(accessToken);
}
