import { Button, ButtonProps } from "@chakra-ui/react";
import React from "react";

export const StandardButton: React.FC<ButtonProps> = ({ children, ...props }) => {
    return <Button
        background="primary.blue"
        padding="5"
        rounded="lg"
        shadow="lg"
        {...props}>
        {children}
    </Button>
}