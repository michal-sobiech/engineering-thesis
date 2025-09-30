import { Text } from "@chakra-ui/react";
import { FC } from "react";
import { FullSpaceCenter } from "../FullSpaceCenter";
import { StandardButton } from "../StandardButton";
import { StanadardPanel } from "../StandardPanel";

export interface SignUpOrLogInPageProps {
    roleLabel: string;
    onLogInButtonClick: () => void;
    onSignUpButtonClick: () => void;
}

export const SignUpOrLogInPage: FC<SignUpOrLogInPageProps> = ({ roleLabel, onLogInButtonClick, onSignUpButtonClick }) => {
    return <FullSpaceCenter>
        <StanadardPanel>
            <Text fontSize="xxl">{roleLabel}</Text>
            <StandardButton onClick={onLogInButtonClick}>
                Log In
            </StandardButton>
            <StandardButton onClick={onSignUpButtonClick}>
                Sign up
            </StandardButton>
        </StanadardPanel>
    </FullSpaceCenter>;
}