import { Text } from "@chakra-ui/react";
import { StandardButton } from "../../../../common/StandardButton";
import { StanadardPanel } from "../../../../common/StandardPanel";
import { StandardTextField } from "../../../../common/StandardTextField";
import { toastError } from "../../../../utils/toast";
import { useContextOrThrow } from "../../../../utils/useContextOrThrow";
import { employeeCreationWizardContext } from "../wizard/EmployeeCreationWizardContext";

export const EmployeeCreationEnterNamePagePanel = () => {
    const { incrementStep, firstName, setFirstName, lastName, setLastName } = useContextOrThrow(employeeCreationWizardContext);

    const onClick = () => {
        if (firstName === "") {
            toastError("First name can't be empty");
            return;
        }

        if (lastName === "") {
            toastError("Last name can't be empty");
            return;
        }

        incrementStep();
    }

    return <StanadardPanel>
        <Text textAlign="center">
            Step 2: enter employee's name
        </Text>
        <StandardTextField
            text={firstName}
            setText={setFirstName}
            placeholder="First name"
        />
        <StandardTextField
            text={lastName}
            setText={setLastName}
            placeholder="Last name"
        />
        <StandardButton
            onClick={onClick}>
            Next
        </StandardButton>
    </StanadardPanel>;
}