import { Box, Center, Text } from "@chakra-ui/react";
import { FC, useState } from "react";
import EmailField from "../EmailField";
import { StanadardPanel } from "../StandardPanel";
import { LogInButton } from "./LogInButton";
import { logInContext, LogInContextValue } from "./LogInContext";
import { LogInPasswordField } from "./LogInPasswordField";

export interface LogInPageProps {
    landingPageUrl: string,
}

export const LogInPage: FC<LogInPageProps> = ({ landingPageUrl }) => {
    const [email, setEmail] = useState<string>("");
    const [password, setPassword] = useState<string>("");

    const contextValue: LogInContextValue = { email, password, landingPageUrl };

    return <Center height="100vh">
        <Box width="40vw">
            <logInContext.Provider value={contextValue}>
                <StanadardPanel>
                    <Text textAlign="center">
                        Log in
                    </Text>
                    <EmailField text={email} setText={setEmail} />
                    <LogInPasswordField text={password} setText={setPassword} />
                    <LogInButton />
                </StanadardPanel>
            </logInContext.Provider>
        </Box>
    </Center>;
};