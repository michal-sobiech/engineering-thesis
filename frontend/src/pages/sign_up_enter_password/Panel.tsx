
import { Flex } from "@chakra-ui/react";
import { useState } from "react";
import CreateAccountButton from "./CreateAccountButton";
import PasswordField from "./PasswordField";

const SignUpPanel = () => {
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
        <PasswordField text={password} setText={setPassword} label={passwordLabel} />
        <CreateAccountButton />
    </Flex>;

};

export default SignUpPanel;