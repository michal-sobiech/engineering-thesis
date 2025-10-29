import { Flex, Text } from "@chakra-ui/react";
import { ok, ResultAsync } from "neverthrow";
import { useState } from "react";
import { toast } from "react-toastify";
import { independentEndUsersApi } from "../../../../api/independent-end-users-api";
import { matchesEmailPattern } from "../../../../utils/email";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../../../utils/error";
import { errorErrResultAsyncFromPromise } from "../../../../utils/result";
import { toastError } from "../../../../utils/toast";
import { useContextOrThrow } from "../../../../utils/useContextOrThrow";
import EmailField from "../../../EmailField";
import { signUpWizardContext } from "../../wizard/SignUpWizardContext";
import { SignUpEnterEmailNextButton } from "./SignUpEnterEmailNextButton";

export const SignUpEnterEmailPanel = () => {
    const [loading, setLoading] = useState<boolean>(false);
    const { incrementStep, email, setEmail } = useContextOrThrow(signUpWizardContext);

    const onNextButtonClick = async () => {
        const result = await isEmailValid(email);
        if (!result.isOk()) {
            toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
            return;
        }

        const emailIsAvailable = result.value;
        if (emailIsAvailable) {
            toast.dismiss();
            incrementStep();
        }
        else {
            toastError("Email is not available");
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

function isEmailValid(email: string): ResultAsync<boolean, Error> {
    return ok(matchesEmailPattern(email))
        .asyncAndThen(() => checkEmailAvailable(email));
}

function checkEmailAvailable(email: string): ResultAsync<boolean, Error> {
    const promise = independentEndUsersApi.checkIndependentEndUserEmailExists(email);
    return errorErrResultAsyncFromPromise(promise)
        .map(response => !response.isExisting);
} 