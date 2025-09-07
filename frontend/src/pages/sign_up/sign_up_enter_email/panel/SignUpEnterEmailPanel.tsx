import { Flex, Text } from "@chakra-ui/react";
import { useState } from "react";
import { toast } from "react-toastify";
import { independentEndUsersApi } from "../../../../api/independent-end-users-api";
import EmailField from "../../../../common/EmailField";
import { useContextOrThrow } from "../../../../utils/useContextOrThrow";
import { signUpWizardContext } from "../../wizard/SignUpWizardContext";
import { SignUpEnterEmailNextButton } from "./SignUpEnterEmailNextButton";

export const SignUpEnterEmailPanel = () => {
    const [loading, setLoading] = useState<boolean>(false);
    const { incrementStep, email, setEmail } = useContextOrThrow(signUpWizardContext);

    const onNextButtonClick = async () => {
        setLoading(true);
        try {
            const isEmailAvailable = await checkEmailAvailable(email);
            if (isEmailAvailable) {
                incrementStep();
            } else {
                toast.info("Chosen email address is not available", { autoClose: 10000 });
            }
        } catch {
            toast.error("Couldn't check whether this email is available. Try again later");
        } finally {
            setLoading(false);
        }
    };

    return <Flex
        bg="white"
        p="5"
        rounded="lg"
        shadow="lg"
        direction="column"
        gap="10px">
        <Text textAlign="center">
            Step 1: enter your email address
        </Text>
        <EmailField text={email} setText={setEmail} />
        <SignUpEnterEmailNextButton onClick={onNextButtonClick} />
    </Flex>
}

async function checkEmailAvailable(email: string): Promise<boolean> {
    const { isExisting } = await independentEndUsersApi.checkIndependentEndUserEmailExists(email);
    return isExisting;
}