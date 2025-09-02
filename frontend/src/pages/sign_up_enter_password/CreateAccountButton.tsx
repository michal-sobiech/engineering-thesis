import { Button } from "@chakra-ui/react";

const CreateAccountButton = () => {
    return <Button
        bg="primary.blue"
        p="5"
        rounded="lg"
        shadow="lg"
        direction="column"
        gap="10px">
        Create account
    </Button>;
}

export default CreateAccountButton;