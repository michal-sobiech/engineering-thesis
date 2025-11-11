import { Text } from "@chakra-ui/react";
import { StandardButton } from "../../../../common/StandardButton";
import { StandardFlex } from "../../../../common/StandardFlex";
import { StandardPanel } from "../../../../common/StandardPanel";
import { StandardTextField } from "../../../../common/StandardTextField";
import { useContextOrThrow } from "../../../../hooks/useContextOrThrow";
import { toastError } from "../../../../utils/toast";
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

    return <StandardPanel>
        <StandardFlex>
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
        </StandardFlex>
    </StandardPanel>;
}