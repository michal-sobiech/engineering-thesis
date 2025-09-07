import { Flex, Text } from "@chakra-ui/react";
import { SignUpEnterNameNextButton } from "./SignUpEnterNameNextButton";
import { SignUpFirstNameField } from "./SignUpFirstNameField";
import SignUpLastNameField from "./SignUpLastNameField";

export const SignUpEnterNamePanel = () => {

    return <Flex
        direction="column"
        bg="white"
        p="5"
        rounded="lg"
        shadow="lg"
        gap="10px"
        width="full"
        height="full">
        <Text textAlign="center">
            Step 2: enter your name
        </Text>
        <SignUpFirstNameField />
        <SignUpLastNameField />
        <SignUpEnterNameNextButton />
    </Flex>;
}