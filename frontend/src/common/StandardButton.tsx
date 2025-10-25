import { Button, ButtonProps } from "@chakra-ui/react";
import React, { useState } from "react";
import { sleep } from "../utils/sleep";

const DEFAULT_DELAY_MS = 250;

export type StandardButtonProps = ButtonProps & {
    delayMs?: number;
}

export const StandardButton: React.FC<StandardButtonProps> = ({ children, delayMs, ...props }) => {
    const [disabled, setDisabled] = useState<boolean>(false);

    const finalDelayMs = delayMs === undefined
        ? DEFAULT_DELAY_MS
        : delayMs;

    function decorateOnClick(onClick: React.MouseEventHandler<HTMLButtonElement>): React.MouseEventHandler<HTMLButtonElement> {
        return async (event: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
            setDisabled(true);
            await sleep(finalDelayMs);
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