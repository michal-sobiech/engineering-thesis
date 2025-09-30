import { Flex, Input } from "@chakra-ui/react";
import { useContextOrThrow } from "../../../../utils/useContextOrThrow";
import { signUpWizardContext } from "../../wizard/SignUpWizardContext";

const SignUpLastNameField = () => {
    const { lastName, setLastName } = useContextOrThrow(signUpWizardContext);

    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setLastName(event.target.value);
    };

    return <Flex direction="column">
        <Input
            type="text"
            name="last_name"
            placeholder="Last name "
            value={lastName}
            onChange={handleChange}
            required
        />
    </Flex>

}

export default SignUpLastNameField;