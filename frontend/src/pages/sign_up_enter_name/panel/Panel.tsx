import { Flex, Text } from "@chakra-ui/react";
import { useState } from "react";
import FirstNameField from "./FirstNameField";
import ProceedButton from "./ProceedButton";
import SurnameField from "./SurnameField";

const Panel = () => {
    const [firstName, setFirstName] = useState<string>("");
    const [firstNameLabel, setFirstNameLabel] = useState<string>("");

    const [surname, setSurname] = useState<string>("");
    const [surnameLabel, setSurnameLabel] = useState<string>("");

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
        <FirstNameField
            text={firstName}
            setText={setFirstName}
            label={firstNameLabel}
        />
        <SurnameField
            text={surname}
            setText={setSurname}
            label={surnameLabel}
        />
        <ProceedButton />
    </Flex>;
}

export default Panel;