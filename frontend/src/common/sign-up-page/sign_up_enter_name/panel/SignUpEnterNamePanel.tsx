import { Text } from "@chakra-ui/react";
import { StanadardPanel } from "../../../StandardPanel";
import { SignUpEnterNameNextButton } from "./SignUpEnterNameNextButton";
import { SignUpFirstNameField } from "./SignUpFirstNameField";
import SignUpLastNameField from "./SignUpLastNameField";

export const SignUpEnterNamePanel = () => {

    return <StanadardPanel>
        <Text textAlign="center">
            Step 2: enter your name
        </Text>
        <SignUpFirstNameField />
        <SignUpLastNameField />
        <SignUpEnterNameNextButton />
    </StanadardPanel>;
}