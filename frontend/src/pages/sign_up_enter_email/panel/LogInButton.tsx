import { Button } from "@chakra-ui/react";

const LogInButton = () => {
    return <Button
        bg="primary.blue"
        p="5"
        rounded="lg"
        shadow="lg"
        direction="column"
        gap="10px">
        Log in
    </Button>;
}

export default LogInButton;