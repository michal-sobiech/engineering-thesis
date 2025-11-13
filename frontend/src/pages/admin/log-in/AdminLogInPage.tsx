import { Center, Text } from "@chakra-ui/react"
import { useState } from "react"
import { useNavigate } from "react-router"
import { useAuthApi } from "../../../api/auth-api"
import { StandardButton } from "../../../common/StandardButton"
import { StandardFlex } from "../../../common/StandardFlex"
import { StandardPanel } from "../../../common/StandardPanel"
import { StandardTextField } from "../../../common/StandardTextField"
import { routes } from "../../../router/routes"
import { logInAdmin } from "../../../services/admin-auth"
import { toastError } from "../../../utils/toast"

export const AdminLogInPage = () => {
    const authApi = useAuthApi();
    const navigate = useNavigate();

    const [username, setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");

    const onClick = async () => {
        const logInResult = await logInAdmin(username, password, authApi);

        if (logInResult.isOk()) {
            navigate(routes.regularAdminLandingPage);
        } else {
            toastError(logInResult.error.message);
        }
    }

    return <Center height="100%">
        <StandardPanel>
            <StandardFlex>
                <Text textAlign="center">
                    Log in as regular admin
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