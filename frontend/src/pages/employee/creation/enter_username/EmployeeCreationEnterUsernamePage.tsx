import { Center, Text } from "@chakra-ui/react";
import { ResultAsync } from "neverthrow";
import { useParams } from "react-router";
import { toast } from "react-toastify";
import { useEnterpriseEmployeesApi } from "../../../../api/enterprise-employees-api";
import { StandardButton } from "../../../../common/StandardButton";
import { StandardFlex } from "../../../../common/StandardFlex";
import { StandardPanel } from "../../../../common/StandardPanel";
import { StandardTextField } from "../../../../common/StandardTextField";
import { EnterpriseEmployeesApi } from "../../../../GENERATED-api";
import { useContextOrThrow } from "../../../../hooks/useContextOrThrow";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../../../utils/error";
import { assertDefined, errorErrResultAsyncFromPromise, fromResult } from "../../../../utils/result";
import { toInt } from "../../../../utils/string";
import { toastError } from "../../../../utils/toast";
import { employeeCreationWizardContext } from "../wizard/EmployeeCreationWizardContext";

export const EmployeeCreationEnterUsernamePage = () => {
    const enterpriseEmployeesApi = useEnterpriseEmployeesApi();
    const { enterpriseId } = useParams<{ enterpriseId: string }>();
    const { incrementStep, username, setUsername } = useContextOrThrow(employeeCreationWizardContext);

    const onNextButtonClick = async () => {
        if (username === "") {
            toastError("Username can't be empty");
            return;
        }

        const result = await fromResult(assertDefined(enterpriseId))
            .map(enterpriseId => toInt(enterpriseId))
            .andThen(enterpriseId => assertDefined(enterpriseId))
            .andThen(enterpriseId => checkUsernameAvailable(enterpriseId, username, enterpriseEmployeesApi));
        if (result.isOk()) {
            const usernameExists = result.value;
            if (usernameExists) {
                toastError("Chosen username is not available");
            } else {
                toast.dismiss();
                incrementStep();
            }
        } else {
            toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
        }
    };

    return <Center height="100vh">
        <StandardPanel>
            <StandardFlex>
                <Text textAlign="center">
                    Step 1: enter the username of the new employee
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
            </StandardFlex>
        </StandardPanel >
    </Center>;
}

function checkUsernameAvailable(enterpriseId: number, username: string, enterpriseEmployeesApi: EnterpriseEmployeesApi): ResultAsync<boolean, Error> {
    const promise = enterpriseEmployeesApi.checkEmployeeUsernameExists(enterpriseId, username);
    return errorErrResultAsyncFromPromise(promise)
        .map(response => !response.isExisting);
} 