import { Center, Text } from "@chakra-ui/react"
import { useEffect, useState } from "react"
import { useNavigate } from "react-router"
import { authApi } from "../../../api/auth-api"
import { enterprisesApi } from "../../../api/enterprises-api"
import { StandardButton } from "../../../common/StandardButton"
import { StandardFlex } from "../../../common/StandardFlex"
import { StandardPanel } from "../../../common/StandardPanel"
import { StandardTextField } from "../../../common/StandardTextField"
import { useIntParam } from "../../../hooks/useIntParam"
import { routes } from "../../../router/routes"
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../../utils/error"
import { errorErrResultAsyncFromPromise } from "../../../utils/result"
import { toastError } from "../../../utils/toast"

export const EmployeeLogInPage = () => {
    const navigate = useNavigate();
    const enterpriseId = useIntParam("enterpriseId");

    const [enterpriseName, setEnterpriseName] = useState<string>("");
    const [username, setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");

    useEffect(() => {
        async function loadEnterpriseData(): Promise<void> {
            const result = await errorErrResultAsyncFromPromise(enterprisesApi.getEnterprise(enterpriseId));
            if (result.isOk()) {
                setEnterpriseName(result.value.enterpriseName);
            } else {
                navigate(routes.mainPage, { replace: true });
            }
        }
        loadEnterpriseData();
    })

    const onClick = async () => {
        const requestParams = { logInEnterpriseEmployeeRequest: { enterpriseId, username, password } };
        const response = await authApi.logInEnterpriseEmployeeRaw(requestParams);
        const status = response.raw.status;
        if (status === 200) {
            navigate(routes.enterprisePublic(enterpriseId));
        } else if (status === 401) {
            toastError("Invalid credentials");
        } else {
            toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
        }
    }

    return <Center height="100vh">
        <StandardPanel>
            <StandardFlex>
                <Text textAlign="center">
                    Log in to "{enterpriseName}"
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