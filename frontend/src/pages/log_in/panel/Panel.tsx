import { Flex, Text } from "@chakra-ui/react";
import { useState } from "react";
import EmailField from "../../../common/EmailField";
import PasswordField from "../../../common/PasswordField";
import LogInButton from "../../sign_up_enter_email/panel/LogInButton";

const Panel = () => {
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
        <EmailField text={username} setText={setUsername} label={usernameLabel} />
        <PasswordField text={password} setText={setPassword} label={passwordLabel} />
        <LogInButton />
    </Flex>;
};

export default Panel;