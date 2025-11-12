import { Box, Center, Text } from "@chakra-ui/react";
import { FC, useState } from "react";
import EmailField from "../EmailField";
import { StandardFlex } from "../StandardFlex";
import { StandardPanel } from "../StandardPanel";
import { IndependentEndUserGroup } from "../UserGroup";
import { IndependentEndUserLogInContextValue, logInContext } from "./IndependentEndUserLogInContext";

export interface IndependentEndUserLogInPageProps {
    userGroup: IndependentEndUserGroup,
    landingPageUrl: string,
}

export const IndependentEndUserLogInPage: FC<IndependentEndUserLogInPageProps> = ({ userGroup, landingPageUrl }) => {
    const [email, setEmail] = useState<string>("");
    const [password, setPassword] = useState<string>("");

    const contextValue: IndependentEndUserLogInContextValue = { email, password, userGroup, landingPageUrl };

    return <Center height="100vh">
        <Box width="40vw">
            <logInContext.Provider value={contextValue}>
                <StandardPanel>
                    <StandardFlex>
                        <Text textAlign="center">
                            Log in
                        </Text>
                        <EmailField text={email} setText={setEmail} />
                        <IndependentEndUserLogInPasswordField text={password} setText={setPassword} />
                        <IndependentEndUserLogInButton />
                    </StandardFlex>
                </StandardPanel>
            </logInContext.Provider>
        </Box>
    </Center>;
};