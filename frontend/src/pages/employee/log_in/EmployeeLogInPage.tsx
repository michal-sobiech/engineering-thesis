import { Center, Text } from "@chakra-ui/react"
import { useState } from "react"
import { authApi } from "../../../api/auth-api"
import { StandardButton } from "../../../common/StandardButton"
import { StanadardPanel } from "../../../common/StandardPanel"
import { StandardTextField } from "../../../common/StandardTextField"

export const EmployeeLogInPage = () => {
    const [username, setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");

    const onClick = () => {
        const promise = authApi.logInEnterpriseEmployee()
    }

    return <Center height="100vh">
        <StanadardPanel>
            <Text textAlign="center">
                Log in to enterprise "SAMPLE TEXT"
            </Text>
            <StandardTextField text={username} setText={setUsername} placeholder="Username" />
            <StandardTextField text={password} setText={setPassword} placeholder="Password" type="password" />
            <StandardButton>
                Log in
            </StandardButton>
        </StanadardPanel>
    </Center>
}