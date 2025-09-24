import { Box, Center, Text } from "@chakra-ui/react";
import { toast } from "react-toastify";
import { enterpriseEmployeesApi } from "../../../../api/enterprise-employees-api";
import { StandardButton } from "../../../../common/StandardButton";
import { StanadardPanel } from "../../../../common/StandardPanel";
import { StandardTextField } from "../../../../common/StandardTextField";
import { errorErrResultAsyncFromPromise } from "../../../../utils/result";
import { toastError } from "../../../../utils/toast";
import { useContextOrThrow } from "../../../../utils/useContextOrThrow";
import { employeeCreationWizardContext } from "../wizard/EmployeeCreationWizardContext";

export const EmployeeCreationEnterPasswordPage = () => {
    const { incrementStep, enterpriseId, username, firstName, lastName, password, setPassword } = useContextOrThrow(employeeCreationWizardContext);

    const onNextButtonClick = async () => {
        const promise = enterpriseEmployeesApi.createEnterpriseEmployee(enterpriseId, { username, firstName, lastName, password });
        const result = await errorErrResultAsyncFromPromise(promise);
        if (!result.isOk()) {
            toastError("Couldn't create employee account. Please try again later.");
        } else {
            toast.dismiss();
            incrementStep();
        }
    };

    return <Center height="100vh">
        <Box width="40vw">
            <StanadardPanel>
                <Text textAlign="center">
                    Step 3: choose password for the new employee
                </Text>
                <StandardTextField
                    type="password"
                    text={password}
                    setText={setPassword}
                    placeholder="Password"
                />
                <StandardButton
                    onClick={onNextButtonClick}>
                    Next
                </StandardButton>
            </StanadardPanel>
        </Box>
    </Center>;
}