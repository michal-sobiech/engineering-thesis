import { Button, ButtonProps } from "@chakra-ui/react";
import React from "react";

export const SignUpEnterEmailNextButton: React.FC<ButtonProps> = ({ children, ...props }) => {
    return <Button
        bg="primary.blue"
        p="5"
        rounded="lg"
        shadow="lg"
        direction="column"
        gap="10px"
        {...props}>
        {children}
    </Button>;
}