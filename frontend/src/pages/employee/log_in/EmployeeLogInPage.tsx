import { Center, Text } from "@chakra-ui/react"
import { useState } from "react"
import { useNavigate } from "react-router"
import { useAuthApi } from "../../../api/auth-api"
import { useUsersApi } from "../../../api/user-api"
import { useAuth } from "../../../auth/useAuth"
import { setJwtTokenInLocalStorage } from "../../../common/local-storage"
import { StandardButton } from "../../../common/StandardButton"
import { StandardFlex } from "../../../common/StandardFlex"
import { StandardIntInput } from "../../../common/StandardIntInput"
import { StandardPanel } from "../../../common/StandardPanel"
import { StandardTextField } from "../../../common/StandardTextField"
import { routes } from "../../../router/routes"
import { logInEmployee } from "../../../services/employee-auth"
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../../utils/error"
import { toastError } from "../../../utils/toast"

export const EmployeeLogInPage = () => {
    const navigate = useNavigate();

    const authApi = useAuthApi();
    const usersApi = useUsersApi();
    const { setAuth } = useAuth();

    const [enterpriseId, setEnterpriseId] = useState<number | null>(null);
    const [username, setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");

    const onClick = async () => {
        if (enterpriseId === null) {
            toastError("Enter the id of your enterprise");
            return;
        }

        const result = await logInEmployee(enterpriseId, username, password, authApi, usersApi);

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
        setAuth({
            isAuthenticated: true,
            jwtToken: result.value.jwt,
            userGroup: "EMPLOYEE"
        })
        setJwtTokenInLocalStorage(result.value.jwt);
        navigate(routes.employeeLandingPage);
    }

    return <Center height="100%">
        <StandardPanel>
            <StandardFlex>
                <Text textAlign="center">
                    Log in as employee
                </Text>
                <StandardIntInput value={enterpriseId} setValue={setEnterpriseId} placeholder="Enterprise id" />
                <StandardTextField text={username} setText={setUsername} placeholder="Username" />
                <StandardTextField text={password} setText={setPassword} placeholder="Password" type="password" />
                <StandardButton onClick={onClick}>
                    Log in
                </StandardButton>
            </StandardFlex>
        </StandardPanel>
    </Center>
}