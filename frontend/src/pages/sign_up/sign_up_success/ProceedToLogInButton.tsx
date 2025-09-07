import { Button } from "@chakra-ui/react";

const ProceedToLogInButton = () => {
    return <Button
        bg="primary.blue"
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