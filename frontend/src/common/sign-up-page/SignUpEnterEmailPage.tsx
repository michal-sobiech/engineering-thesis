import { Box, Center, Text } from "@chakra-ui/react";
import { FC } from "react";
import { toast } from "react-toastify";
import { useIndependentEndUsersApi } from "../../api/independent-end-users-api";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../utils/error";
import { toastError } from "../../utils/toast";
import { isEmailValidAndAvailable } from "../email";
import EmailField from "../EmailField";
import { StandardButton } from "../StandardButton";
import { StandardFlex } from "../StandardFlex";
import { VoidCallback } from "../VoidCallback";

export interface SignUpEnterEmailPageProps {
    submit: () => void;
    email: string,
    setEmail: VoidCallback<string>
}

export const SignUpEnterEmailPage: FC<SignUpEnterEmailPageProps> = ({ submit, email, setEmail }) => {
    const independentEndUsersApi = useIndependentEndUsersApi();

    const onNextButtonClick = async () => {
        const result = await isEmailValidAndAvailable(email, independentEndUsersApi);
        if (!result.isOk()) {
            toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
            return;
        }

        const emailIsAvailable = result.value;
        if (emailIsAvailable) {
            toast.dismiss();
            submit();
        }
        else {
            toastError("Email is not available");
        }
    };

    return <Center height="100%">
        <Box width="40%">
            <StandardFlex>
                <Text textAlign="center">
                    Enter your email address
                </Text>
                <EmailField text={email} setText={setEmail} />
                <StandardButton onClick={onNextButtonClick}>
                    Next
                </StandardButton>
            </StandardFlex>
        </Box>
    </Center>;
}
