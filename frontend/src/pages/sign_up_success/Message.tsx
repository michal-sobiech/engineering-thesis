import { Text } from "@chakra-ui/react";
import colors from "../../colors";

const Message = () => {
    return <div>
        <Text as="span" color={colors.lightGreen} fontWeight="bold">
            Successful
        </Text>
        {" "}
        sign up!
    </div>;
}

export default Message;