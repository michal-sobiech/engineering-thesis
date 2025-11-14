import { ResultAsync } from "neverthrow";
import { useUsersApi } from "../api/user-api";
import { getJwtFromLocalStorage, removeJwtTokenFromLocalStorage } from "../common/local-storage";
import { isUserGroup } from "../common/UserGroup";
import { errorErrResultAsyncFromPromise } from "../utils/result";
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

        console.log("jwt: ", jwt);
        const userGroupPromise = userApi.getMyUserGroup({ headers: { Authorization: `Bearer ${jwt}` } });
        const userGroupResult = await errorErrResultAsyncFromPromise(userGroupPromise);
        if (userGroupResult.isErr()) {
            removeJwtTokenFromLocalStorage();
            setAuth({ isAuthenticated: false });
            return;
        }

        const userGroup = userGroupResult.value.userGroup;
        if (!isUserGroup(userGroup)) {
            removeJwtTokenFromLocalStorage();
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