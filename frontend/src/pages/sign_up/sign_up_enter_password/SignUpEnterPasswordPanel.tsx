
import { Flex, Text } from "@chakra-ui/react";
import PasswordField from "../../../common/PasswordField";
import { useContextOrThrow } from "../../../utils/useContextOrThrow";
import { signUpWizardContext } from "../wizard/SignUpWizardContext";
import { CreateAccountButton } from "./CreateAccountButton";

export const SignUpEnterPasswordPanel = () => {
    const { password, setPassword } = useContextOrThrow(signUpWizardContext);

    return <Flex bg="white"
        p="5"
        rounded="lg"
        shadow="lg"
        direction="column"
        gap="10px">
        <Text textAlign="center">
            Step 3: set a password
        </Text>
        <PasswordField text={password} setText={setPassword} />
        <CreateAccountButton />
    </Flex>;
};