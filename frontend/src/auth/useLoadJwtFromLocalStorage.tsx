import { ResultAsync } from "neverthrow";
import { useStdFetchUserApi } from "../api/std-fetch-user-api";
import { getJwtFromLocalStorage, removeJwtTokenFromLocalStorage } from "../common/local-storage";
import { isUserGroup } from "../common/UserGroup";
import { fetchMyUserGroup } from "../services/user-group";
import { errorErrResultAsyncFromPromise } from "../utils/result";
import { useAuth } from "./useAuth";

export function useLoadJwtFromLocalStorage() {
    const { setAuth } = useAuth();
    const stdFetchUserApi = useStdFetchUserApi();

    async function createPromise(): Promise<void> {
        const jwt = getJwtFromLocalStorage();
        if (jwt === null) {
            setAuth({ isAuthenticated: false });
            return;
        }

        const userGroupPromise = fetchMyUserGroup(jwt, stdFetchUserApi);
        const userGroupResult = await errorErrResultAsyncFromPromise(userGroupPromise);
        if (userGroupResult.isErr()) {
            removeJwtTokenFromLocalStorage();
            setAuth({ isAuthenticated: false });
            return;
        }

        const userGroup = userGroupResult.value;
        if (!isUserGroup(userGroup)) {
            removeJwtTokenFromLocalStorage();
            setAuth({ isAuthenticated: false });
            return;
        }

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