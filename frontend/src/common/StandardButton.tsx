import { Button, ButtonProps } from "@chakra-ui/react";
import React, { useState } from "react";
import { sleep } from "../utils/sleep";

const DEFAULT_DELAY_MS = 250;

export type StandardButtonProps = ButtonProps & {
    delayMs?: number;
}

export const StandardButton: React.FC<StandardButtonProps> = ({ delayMs, children, onClick, disabled, ...props }) => {
    const [onCooldown, setOnCooldown] = useState<boolean>(false);

    const finalDelayMs = delayMs ?? DEFAULT_DELAY_MS;

    const decoratedOnClick = async (event: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
        setOnCooldown(true);
        await sleep(finalDelayMs);
        onClick?.(event);
        setOnCooldown(false);
    }

    const isDisabled = disabled || onCooldown;

    return <Button
        background="primary.blue"
        padding="5"
        rounded="lg"
        shadow="lg"
        {...props}
        onClick={decoratedOnClick}
        disabled={isDisabled}>
        {children}
    </Button >;
}