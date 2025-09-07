import { Flex, Input } from "@chakra-ui/react";
import { useContextOrThrow } from "../../../../utils/useContextOrThrow";
import { signUpWizardContext } from "../../wizard/SignUpWizardContext";

export const SignUpFirstNameField = () => {
    const { firstName, setFirstName } = useContextOrThrow(signUpWizardContext);

    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setFirstName(event.target.value);
    };

    return <Flex direction="column">
        <Input
            type="text"
            name="first_name"
            placeholder="First name"
            value={firstName}
            onChange={handleChange}
            required
        />
    </Flex>

}