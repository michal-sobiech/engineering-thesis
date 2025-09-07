import { Text } from "@chakra-ui/react";

const Message = () => {
    return <div>
        <Text as="span" color="primary.green" fontWeight="bold">
            Successful
        </Text>
        {" "}
        sign up!
    </div>;
}

export default Message;