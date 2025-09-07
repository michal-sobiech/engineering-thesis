import { Button } from "@chakra-ui/react";
import React from "react";

export interface StandardButtonProps {
    text: string,
    onClick: React.MouseEventHandler<HTMLButtonElement>
}

export const StandardButton: React.FC<StandardButtonProps> = ({ text, onClick }) => {
    return <Button
        background="primary.blue"
        padding="5"
        rounded="lg"
        shadow="lg"
        onClick={onClick}>
        {text}
    </Button>
}