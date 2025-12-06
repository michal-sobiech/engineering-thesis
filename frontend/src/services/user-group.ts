import { isUserGroup, UserGroup } from "../common/UserGroup";
import { ResponseError, UserApi } from "../GENERATED-api";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../utils/error";

export async function fetchMyUserGroup(accessToken: string, userApi: UserApi): Promise<UserGroup> {
    try {
        const userGroupPromise = await userApi.getMyUserGroup({ headers: { Authorization: `Bearer ${accessToken}` } });
        if (!isUserGroup(userGroupPromise.userGroup)) {
            throw new Error(DEFAULT_ERROR_MESSAGE_FOR_USER);
        }
        return userGroupPromise.userGroup;
    } catch (error: unknown) {
        if (error instanceof ResponseError && error.response.status === 401) {
            throw new Error("Unauthorized");
        }
    }
    throw new Error(DEFAULT_ERROR_MESSAGE_FOR_USER);
}