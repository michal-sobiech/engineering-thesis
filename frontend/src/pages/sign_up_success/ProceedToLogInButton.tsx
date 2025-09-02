import { Button } from "@chakra-ui/react";
import colors from "../../colors";

const ProceedToLogInButton = () => {
    return <Button
        bg={colors.blue}
        p="5"
        rounded="lg"
        shadow="lg"
        direction="column"
        gap="10px"
        fontSize="xl">
        Log in
    </Button>;
}

export default ProceedToLogInButton;