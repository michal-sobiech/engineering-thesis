import { Flex, Text } from "@chakra-ui/react";
import { ResultAsync } from "neverthrow";
import { toast } from "react-toastify";
import { enterpriseEmployeesApi } from "../../../../api/enterprise-employees-api";
import { StandardButton } from "../../../../common/StandardButton";
import { StandardTextField } from "../../../../common/StandardTextField";
import { defaultStringErrResultAsyncFromPromise } from "../../../../utils/result";
import { toastError } from "../../../../utils/toast";
import { useContextOrThrow } from "../../../../utils/useContextOrThrow";
import { employeeCreationWizardContext } from "../wizard/EmployeeCreationWizardContext";

export const EmployeeCreationEnterUsernamePage = () => {
    const { enterpriseId, incrementStep, username, setUsername } = useContextOrThrow(employeeCreationWizardContext);

    const onNextButtonClick = async () => {
        const result = await checkUsernameAvailable(enterpriseId, username);
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
            Step 1: enter username of the new employee
        </Text>
        <StandardTextField
            text={username}
            setText={setUsername}
            placeholder="Username"
        />
        <StandardButton
            onClick={onNextButtonClick}>
            Next
        </StandardButton>
    </Flex >
}

function checkUsernameAvailable(enterpriseId: number, username: string): ResultAsync<void, string> {
    const promise = enterpriseEmployeesApi.checkEmployeeUsernameExists(enterpriseId, username);
    const result = defaultStringErrResultAsyncFromPromise(promise);
    const voidResult = result.map(() => { });
    return voidResult;
} 