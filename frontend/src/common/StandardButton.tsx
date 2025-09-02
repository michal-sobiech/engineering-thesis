import { Button } from "@chakra-ui/react";
import React from "react";

const StandardButton: React.FC<{ text: string }> = ({ text }) => {
    return <Button
        background="primary.blue"
        padding="5"
        rounded="lg"
        shadow="lg">
        {text}
    </Button>
}

export default StandardButton;
