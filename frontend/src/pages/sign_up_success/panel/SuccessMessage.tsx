import { Text } from "@chakra-ui/react";

const SuccessMessage = () => {
    return <Text fontSize="1.5rem">
        <Text as="span" color="limegreen">
            Successful
        </Text>
        {" "}sign-up!
    </Text>;
}

export default SuccessMessage;