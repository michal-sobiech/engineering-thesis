import { Text } from "@chakra-ui/react";

export const SignUpSuccessMessage = () => {
    return <Text fontSize="xxl">
        <Text as="span" color="primary.lightGreen" fontWeight="bold" >
            Successful
        </Text>
        {" "}
        sign up!
    </Text>
}