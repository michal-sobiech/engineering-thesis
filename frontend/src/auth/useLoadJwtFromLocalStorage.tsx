import { ResultAsync } from "neverthrow";
import { useUsersApi } from "../api/user-api";
import { isUserGroup } from "../common/UserGroup";
import { errorErrResultAsyncFromPromise } from "../utils/result";
import { getJwtFromLocalStorage } from "./storage";
import { useAuth } from "./useAuth";

export function useLoadJwtFromLocalStorage() {
    const { setAuth } = useAuth();
    const userApi = useUsersApi();

    async function createPromise(): Promise<void> {
        const jwt = getJwtFromLocalStorage();
        if (jwt === null) {
            setAuth({ isAuthenticated: false });
            return;
        }

        const userGroupPromise = userApi.getMyUserGroup({ headers: { Authorization: `Bearer ${jwt}` } });
        const userGroupResult = await errorErrResultAsyncFromPromise(userGroupPromise);
        if (userGroupResult.isErr()) {
            setAuth({ isAuthenticated: false });
            return;
        }

        const userGroup = userGroupResult.value.userGroup;
        if (!isUserGroup(userGroup)) {
            setAuth({ isAuthenticated: false });
            return;
        }

        console.log("set authenticated auth")
        setAuth({
            isAuthenticated: true,
            userGroup,
            jwtToken: jwt
        });
    }

    function fn(): ResultAsync<void, Error> {
        return errorErrResultAsyncFromPromise(createPromise());
    }

    return fn;
};