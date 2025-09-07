import { Flex, Text } from "@chakra-ui/react";
import { useState } from "react";
import EmailField from "../../../common/EmailField";
import PasswordField from "../../../common/PasswordField";

const LogInPanel = () => {
    const [username, setUsername] = useState<string>("");
    const [usernameLabel, setUsernameLabel] = useState<string>("aaa");

    const [password, setPassword] = useState<string>("");
    const [passwordLabel, setPasswordLabel] = useState<string>("");

    const [status, setStatus] = useState<string>("")

    return <Flex bg="white"
        p="5"
        rounded="lg"
        shadow="lg"
        direction="column"
        gap="10px">
        <Text textAlign="center">
            Log in
        </Text>
        <EmailField text={username} setText={setUsername} />
        <PasswordField text={password} setText={setPassword} label={passwordLabel} />
        {/* <ProceedToLogInButton /> */}
    </Flex>;
};

export default LogInPanel;