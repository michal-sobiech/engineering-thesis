import { ResultAsync } from "neverthrow";
import { useUsersApi } from "../api/user-api";
import { UserGroup, userGroups } from "../common/UserGroup";
import { errorErrResultAsyncFromPromise } from "../utils/result";
import { getJwtFromLocalStorage } from "./storage";
import { useAuth } from "./useAuth";

export function useLoadJwtFromLocalStorage() {
    const { setAuth } = useAuth();
    const userApi = useUsersApi();

    async function createPromise(): Promise<void> {
        console.log("loaded");

        const jwt = getJwtFromLocalStorage();
        if (jwt === null) {
            setAuth({ isAuthenticated: false });
            return;
        }
        const userGroupPromise = userApi.getMyUserGroup();
        const userGroupResult = await errorErrResultAsyncFromPromise(userGroupPromise);
        if (userGroupResult.isErr()) {
            setAuth({ isAuthenticated: false });
            return;
        }

        const userGroup = userGroupResult.value.userGroup;
        if (!(userGroup in userGroups)) {
            setAuth({ isAuthenticated: false });
            return;
        }
        const userGroupCast = userGroup as UserGroup;

        setAuth({
            isAuthenticated: true,
            userGroup: userGroupCast,
            jwtToken: jwt
        });
    }

    function fn(): ResultAsync<void, Error> {
        return errorErrResultAsyncFromPromise(createPromise());
    }

    return fn;
};