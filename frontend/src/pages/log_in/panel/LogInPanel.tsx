import { Text } from "@chakra-ui/react";
import { useState } from "react";
import EmailField from "../../../common/EmailField";
import { StanadardPanel } from "../../../common/StandardPanel";
import { logInContext, LogInContextValue } from "../LogInContext";
import { LogInButton } from "./LogInButton";
import { LogInPasswordField } from "./LogInPasswordField";

const LogInPanel = () => {
    const [email, setEmail] = useState<string>("");
    const [password, setPassword] = useState<string>("");

    const contextValue: LogInContextValue = { email, password };

    return <logInContext.Provider value={contextValue}>
        <StanadardPanel>
            <Text textAlign="center">
                Log in
            </Text>
            <EmailField text={email} setText={setEmail} />
            <LogInPasswordField text={password} setText={setPassword} />
            <LogInButton />
        </StanadardPanel>
    </logInContext.Provider>

};

export default LogInPanel;