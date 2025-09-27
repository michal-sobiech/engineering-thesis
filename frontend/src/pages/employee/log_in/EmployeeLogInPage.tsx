import { Center, Text } from "@chakra-ui/react"
import { useState } from "react"
import { useNavigate } from "react-router"
import { authApi } from "../../../api/auth-api"
import { StandardButton } from "../../../common/StandardButton"
import { StanadardPanel } from "../../../common/StandardPanel"
import { StandardTextField } from "../../../common/StandardTextField"
import { useIntParam } from "../../../hooks/useIntParam"
import { routes } from "../../../router/routes"
import { errorErrResultAsyncFromPromise } from "../../../utils/result"
import { useContextOrThrow } from "../../../utils/useContextOrThrow"
import { EnterpriseContext } from "../../enterprise/route/context/EnterpriseContext"

export const EmployeeLogInPage = () => {
    const navigate = useNavigate();
    const enterpriseId = useIntParam("enterpriseId");

    const { enterpriseName } = useContextOrThrow(EnterpriseContext);

    const [username, setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");

    const onClick = async () => {
        const promise = authApi.logInEnterpriseEmployee({ enterpriseId, username, password });
        const result = await errorErrResultAsyncFromPromise(promise);
        if (result.isOk()) {
            navigate(routes.enterprise(enterpriseId));
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