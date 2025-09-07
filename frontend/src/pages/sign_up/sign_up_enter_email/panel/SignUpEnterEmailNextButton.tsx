import { Button } from "@chakra-ui/react";
import React from "react";

export interface SignUpEnterEmailNextButtonProps {
    onClick: React.MouseEventHandler<HTMLButtonElement>,
}

export const SignUpEnterEmailNextButton: React.FC<SignUpEnterEmailNextButtonProps> = ({ onClick }) => {
    return <Button
        bg="primary.blue"
        p="5"
        rounded="lg"
        shadow="lg"
        direction="column"
        gap="10px"
        onClick={onClick}>
        Next
    </Button>;
}