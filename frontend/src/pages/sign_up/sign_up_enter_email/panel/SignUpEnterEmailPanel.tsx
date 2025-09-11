import { Flex, Text } from "@chakra-ui/react";
import { err, ok, Result, ResultAsync } from "neverthrow";
import { useState } from "react";
import { toast } from "react-toastify";
import { independentEndUsersApi } from "../../../../api/independent-end-users-api";
import EmailField from "../../../../common/EmailField";
import { matchesEmailPattern } from "../../../../utils/email";
import { defaultStringErrResultAsyncFromPromise } from "../../../../utils/result";
import { toastError } from "../../../../utils/toast";
import { useContextOrThrow } from "../../../../utils/useContextOrThrow";
import { signUpWizardContext } from "../../wizard/SignUpWizardContext";
import { SignUpEnterEmailNextButton } from "./SignUpEnterEmailNextButton";

export const SignUpEnterEmailPanel = () => {
    const [loading, setLoading] = useState<boolean>(false);
    const { incrementStep, email, setEmail } = useContextOrThrow(signUpWizardContext);

    const onNextButtonClick = async () => {
        const result = await validateEmail(email);
        if (!result.isOk()) {
            toastError(result.error);
        } else {
            toast.dismiss();
            incrementStep();
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
        <SignUpEnterEmailNextButton
            onClick={onNextButtonClick}
            disabled={loading}>
            Next
        </SignUpEnterEmailNextButton>
    </Flex>
}

function validateEmail(email: string): ResultAsync<void, string> {
    return doesEmailMatchPattern(email)
        .asyncAndThen(() => checkEmailAvailable(email));
}

function doesEmailMatchPattern(email: string): Result<void, string> {
    return matchesEmailPattern(email) ? ok() : err("Given email is not a valid email!");
}

function checkEmailAvailable(email: string): ResultAsync<void, string> {
    const promise = independentEndUsersApi.checkIndependentEndUserEmailExists(email);
    const result = defaultStringErrResultAsyncFromPromise(promise);
    const voidResult = result.map(() => { });
    return voidResult;
} 