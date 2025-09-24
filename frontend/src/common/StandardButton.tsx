import { Button, ButtonProps } from "@chakra-ui/react";
import React, { useState } from "react";
import { sleep } from "../utils/sleep";

const DELAY_MS = 250;

export const StandardButton: React.FC<ButtonProps> = ({ children, ...props }) => {
    const [disabled, setDisabled] = useState<boolean>(false);

    function decorateOnClick(onClick: React.MouseEventHandler<HTMLButtonElement>): React.MouseEventHandler<HTMLButtonElement> {
        return async (event: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
            setDisabled(true);
            await sleep(DELAY_MS);
            onClick(event);
            setDisabled(false);
        }
    }

    props.onClick = props.onClick && decorateOnClick(props.onClick);
    props.disabled = disabled;

    return <Button
        background="primary.blue"
        padding="5"
        rounded="lg"
        shadow="lg"
        {...props}>
        {children}
    </Button >
}