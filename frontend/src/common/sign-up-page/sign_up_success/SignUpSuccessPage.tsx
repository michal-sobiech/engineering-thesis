import { Center } from "@chakra-ui/react";
import { FC } from "react";
import { useNavigate } from "react-router";
import { StandardButton } from "../../StandardButton";
import { StandardFlex } from "../../StandardFlex";
import { StandardPanel } from "../../StandardPanel";
import { SignUpSuccessMessage } from "./SignUpSuccessMessage";

export interface SignUpSuccessPage {
    logInPageUrl: string;
}

export const SignUpSuccessPage: FC<SignUpSuccessPage> = ({ logInPageUrl }) => {
    const navigate = useNavigate();

    const onClick = async () => {
        navigate(logInPageUrl);
    }

    return <Center height="100%">
        <StandardPanel width="30%">
            <StandardFlex>
                <Center>
                    <SignUpSuccessMessage />
                </Center>
                <StandardButton onClick={onClick}>
                    Log in
                </StandardButton>
            </StandardFlex>
        </StandardPanel>
    </Center>;
}