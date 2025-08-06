import { Button } from "@chakra-ui/react";
import colors from "../../colors";

const CreateAccountButton = () => {
    return <Button
        bg={colors.blue}
        p="5"
        rounded="lg"
        shadow="lg"
        direction="column"
        gap="10px">
        Create account
    </Button>;
}

export default CreateAccountButton;