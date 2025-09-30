import { Text } from "@chakra-ui/react";

const Message = () => {
    return <Text fontSize="xxl">
        <Text as="span" color="primary.lightGreen" fontWeight="bold" >
            Successful
        </Text>
        {" "}
        sign up!
    </Text>
}

export default Message;