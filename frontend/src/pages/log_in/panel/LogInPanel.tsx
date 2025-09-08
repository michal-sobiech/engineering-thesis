import { Flex, Text } from "@chakra-ui/react";
import { useState } from "react";
import EmailField from "../../../common/EmailField";
import { logInContext, LogInContextValue } from "../LogInContext";
import { LogInButton } from "./LogInButton";
import { LogInPasswordField } from "./LogInPasswordField";

const LogInPanel = () => {
    const [email, setEmail] = useState<string>("");
    const [password, setPassword] = useState<string>("");

    const contextValue: LogInContextValue = { email, password };

    return <logInContext.Provider value={contextValue}>
        <Flex bg="white"
            p="5"
            rounded="lg"
            shadow="lg"
            direction="column"
            gap="10px">
            <Text textAlign="center">
                Log in
            </Text>
            <EmailField text={email} setText={setEmail} />
            <LogInPasswordField text={password} setText={setPassword} />
            <LogInButton />
        </Flex>
    </logInContext.Provider>

};

export default LogInPanel;