import { Center, Text } from "@chakra-ui/react"
import { useState } from "react"
import { useNavigate } from "react-router"
import { useAuthApi } from "../../../api/auth-api"
import { useUsersApi } from "../../../api/user-api"
import { useAuth } from "../../../auth/useAuth"
import { setJwtTokenInLocalStorage } from "../../../common/local-storage"
import { StandardButton } from "../../../common/StandardButton"
import { StandardFlex } from "../../../common/StandardFlex"
import { StandardPanel } from "../../../common/StandardPanel"
import { StandardTextField } from "../../../common/StandardTextField"
import { isUserGroup } from "../../../common/UserGroup"
import { routes } from "../../../router/routes"
import { logInAdmin } from "../../../services/admin-auth"
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../../utils/error"
import { errorErrResultAsyncFromPromise } from "../../../utils/result"
import { toastError } from "../../../utils/toast"

export const AdminLogInPage = () => {
    const navigate = useNavigate();

    const authApi = useAuthApi();
    const usersApi = useUsersApi();

    const { setAuth } = useAuth();

    const [username, setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");

    const onClick = async () => {
        const result = await logInAdmin(username, password, authApi, usersApi);

        if (result.isErr()) {
            toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
            setAuth({ isAuthenticated: false });
            return;
        }
        if (result.value.status === "BAD_CREDENTIALS") {
            toastError("Invalid credentials");
            setAuth({ isAuthenticated: false });
            return;
        }

        const jwt = result.value.jwt;

        const userGroupPromise = usersApi.getMyUserGroup({ headers: { Authorization: `Bearer ${jwt}` } });
        const userGroupResult = await errorErrResultAsyncFromPromise(userGroupPromise);
        if (userGroupResult.isErr()) {
            toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
            setAuth({ isAuthenticated: false });
            return;
        }
        const userGroup = userGroupResult.value.userGroup;
        if (!isUserGroup(userGroup)) {
            toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
            setAuth({ isAuthenticated: false });
            return;
        }

        setAuth({
            isAuthenticated: true,
            jwtToken: result.value.jwt,
            userGroup: userGroup
        })
        setJwtTokenInLocalStorage(result.value.jwt);
        navigate(routes.adminReportsPage);
    }

    return <Center height="100%">
        <StandardPanel>
            <StandardFlex>
                <Text textAlign="center">
                    Log in as admin
                </Text>
                <StandardTextField text={username} setText={setUsername} placeholder="Username" />
                <StandardTextField text={password} setText={setPassword} placeholder="Password" type="password" />
                <StandardButton onClick={onClick}>
                    Log in
                </StandardButton>
            </StandardFlex>
        </StandardPanel>
    </Center>
}