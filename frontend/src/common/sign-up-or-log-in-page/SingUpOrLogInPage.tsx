import { Center, Text } from "@chakra-ui/react";
import { FC } from "react";
import { StandardButton } from "../StandardButton";
import { StandardPanel } from "../StandardPanel";

export interface SignUpOrLogInPageProps {
    roleLabel: string;
    onLogInButtonClick: () => void;
    onSignUpButtonClick: () => void;
}

export const SignUpOrLogInPage: FC<SignUpOrLogInPageProps> = ({ roleLabel, onLogInButtonClick, onSignUpButtonClick }) => {
    return <Center height="100vh">
        <StandardPanel>
            <Text fontSize="xxl">{roleLabel}</Text>
            <StandardButton onClick={onLogInButtonClick}>
                Log In
            </StandardButton>
            <StandardButton onClick={onSignUpButtonClick}>
                Sign up
            </StandardButton>
        </StandardPanel>
    </Center>;
}