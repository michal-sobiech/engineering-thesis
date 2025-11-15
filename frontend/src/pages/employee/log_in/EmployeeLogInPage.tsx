import { Center, Text } from "@chakra-ui/react"
import { useState } from "react"
import { useNavigate } from "react-router"
import { useAuthApi } from "../../../api/auth-api"
import { useEnterprisesApi } from "../../../api/enterprises-api"
import { StandardButton } from "../../../common/StandardButton"
import { StandardFlex } from "../../../common/StandardFlex"
import { StandardIntInput } from "../../../common/StandardIntInput"
import { StandardPanel } from "../../../common/StandardPanel"
import { StandardTextField } from "../../../common/StandardTextField"
import { routes } from "../../../router/routes"
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../../utils/error"
import { toastError } from "../../../utils/toast"

export const EmployeeLogInPage = () => {
    const authApi = useAuthApi();
    const enterprisesApi = useEnterprisesApi();
    const navigate = useNavigate();

    const [enterpriseId, setEnterpriseId] = useState<number | null>(null);
    const [username, setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");

    const onClick = async () => {
        if (enterpriseId === null) {
            toastError("Enter the id of your enterprise");
            return;
        }

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