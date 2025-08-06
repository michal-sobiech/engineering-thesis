import { Button } from "@chakra-ui/react";
import colors from "../../../colors";

const ProceedButton = () => {
    return <Button
        bg={colors.blue}
        p="5"
        rounded="lg"
        shadow="lg"
        direction="column"
        gap="10px">
        Next
    </Button>;
};

export default ProceedButton;