import { Center, Text } from "@chakra-ui/react";
import { FC } from "react";
import { validatePassword } from "../../services/validate-password";
import { toastError } from "../../utils/toast";
import { UseStateSetter } from "../../utils/use-state";
import { StandardButton } from "../StandardButton";
import { StandardFlex } from "../StandardFlex";
import { StandardPanel } from "../StandardPanel";
import { StandardTextField } from "../StandardTextField";

export interface SignUpEnterPasswordPageProps {
    submit: () => Promise<void>;
    password: string;
    setPassword: UseStateSetter<string>;
}

export const SignUpEnterPasswordPage: FC<SignUpEnterPasswordPageProps> = ({ submit, password, setPassword }) => {
    const onClick = async () => {
        const passwordValidationResult = validatePassword(password);
        if (passwordValidationResult.isErr()) {
            toastError(passwordValidationResult.error.message);
            return;
        }
        submit();
    }

    return <Center height="100%">
        <StandardPanel width="40%">
            return <StandardFlex>
                <Text textAlign="center">
                    Set a password
                </Text>

                <StandardTextField
                    text={password}
                    setText={setPassword}
                    type="password"
                />

                <StandardButton onClick={onClick}>
                    Next
                </StandardButton>
            </StandardFlex>;
        </StandardPanel>
    </Center >;
}