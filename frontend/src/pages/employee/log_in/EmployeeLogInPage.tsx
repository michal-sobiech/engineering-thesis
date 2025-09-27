import { Center, Text } from "@chakra-ui/react"
import { useState } from "react"
import { useNavigate } from "react-router"
import { authApi } from "../../../api/auth-api"
import { StandardButton } from "../../../common/StandardButton"
import { StanadardPanel } from "../../../common/StandardPanel"
import { StandardTextField } from "../../../common/StandardTextField"
import { useIntParam } from "../../../hooks/useIntParam"
import { routes } from "../../../router/routes"
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../../utils/error"
import { toastError } from "../../../utils/toast"
import { useContextOrThrow } from "../../../utils/useContextOrThrow"
import { EnterpriseContext } from "../../enterprise/route/context/EnterpriseContext"

export const EmployeeLogInPage = () => {
    const navigate = useNavigate();
    const enterpriseId = useIntParam("enterpriseId");

    const { enterpriseName } = useContextOrThrow(EnterpriseContext);

    const [username, setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");

    const onClick = async () => {
        const requestParams = { logInEnterpriseEmployeeRequest: { enterpriseId, username, password } };
        const response = await authApi.logInEnterpriseEmployeeRaw(requestParams);
        const status = response.raw.status;
        if (status === 200) {
            navigate(routes.enterprise(enterpriseId));
        } else if (status === 401) {
            toastError("Invalid credentials");
        } else {
            toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
        }
    }

    return <Center height="100vh">
        <StanadardPanel>
            <Text textAlign="center">
                Log in to "{enterpriseName}"
            </Text>
            <StandardTextField text={username} setText={setUsername} placeholder="Username" />
            <StandardTextField text={password} setText={setPassword} placeholder="Password" type="password" />
            <StandardButton onClick={onClick}>
                Log in
            </StandardButton>
        </StanadardPanel>
    </Center>
}