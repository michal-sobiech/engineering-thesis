import { ButtonGroup } from "@chakra-ui/react";
import { FC } from "react";
import { StandardButton } from "./StandardButton";
import { VoidCallback } from "./VoidCallback";

export interface BooleanToggleProps {
    isOption1Chosen: boolean,
    setIsOption1Chosen: VoidCallback<boolean>,
    option1Text: string,
    option2Text: string,
}

export const BooleanToggle: FC<BooleanToggleProps> = ({ isOption1Chosen, setIsOption1Chosen, option1Text, option2Text }) => {
    const option1ButtonColor = isOption1Chosen ? "primary.blue" : "primary.lightGray";
    const option2ButtonColor = isOption1Chosen ? "primary.lightGray" : "primary.blue";

    return <ButtonGroup>
        <StandardButton
            background={option1ButtonColor}
            onClick={() => setIsOption1Chosen(true)}>
            {option1Text}
        </StandardButton>
        <StandardButton
            background={option2ButtonColor}
            onClick={() => setIsOption1Chosen(false)}>
            {option2Text}
        </StandardButton>
    </ButtonGroup>;
}