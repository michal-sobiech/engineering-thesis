import { Button, Flex, Text } from "@chakra-ui/react";
import { useState } from "react";
import { config } from "../config/config";
import { joinUrl } from "../utils/UrlUtils";


const createUrl = (): URL => {
    return joinUrl(config.backendUrl, "sign_up");
};

const onClick = () => {
    fetch(createUrl())
        .then(response => response.status);
};

const LabeledButton = () => {
    const [label, setLabel] = useState<string>("a");

    return <Flex direction="column">
        <Button onClick={onClick}>
            test
        </Button>
        <Text display="inline-block"
            fontSize="0.75rem"
            minHeight="0.75rem"
            textIndent="0.5em">
            {label}
        </Text>
    </Flex>;
}

export default LabeledButton;