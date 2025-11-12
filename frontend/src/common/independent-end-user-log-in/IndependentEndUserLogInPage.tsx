import { Box, Flex, Text } from "@chakra-ui/react";
import { FC, useState } from "react";
import EmailField from "../EmailField";
import { StandardFlex } from "../StandardFlex";
import { StandardPanel } from "../StandardPanel";
import { IndependentEndUserGroup } from "../UserGroup";
import { IndependentEndUserLogInButton } from "./IndependentEndUserLogInButton";
import { IndependentEndUserLogInContextValue, logInContext } from "./IndependentEndUserLogInContext";
import { IndependentEndUserLogInPasswordField } from "./IndependentEndUserLogInPasswordField";

export interface IndependentEndUserLogInPageProps {
    userGroup: IndependentEndUserGroup,
    landingPageUrl: string,
}

export const IndependentEndUserLogInPage: FC<IndependentEndUserLogInPageProps> = ({ userGroup, landingPageUrl }) => {
    const [email, setEmail] = useState<string>("");
    const [password, setPassword] = useState<string>("");

    const contextValue: IndependentEndUserLogInContextValue = { email, password, userGroup, landingPageUrl };

    console.log("eeee")

    return <Flex height="100%" align="center" justify="center">
        <Box width="60%">
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
    </Flex>;
};