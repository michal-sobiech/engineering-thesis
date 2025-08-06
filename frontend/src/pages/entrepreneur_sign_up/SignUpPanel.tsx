
import { Flex } from "@chakra-ui/react";
import { useState } from "react";
import LabeledButton from "../../components/LabeledButton";
import PasswordField from "../../components/password_field/PasswordField";
import UsernameField from "../../components/username_field/UsernameField";

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
        gap="10px"
        width="50vw"
        height="60vh">
        <UsernameField {...{ username, setUsername, label: usernameLabel }} />
        <PasswordField {...{ password, setPassword, label: passwordLabel }} />
        <LabeledButton />
    </Flex>;

};

export default SignUpPanel;