import { FC, useState } from "react";
import { BooleanToggle } from "./BooleanToggle";
import { StandardFlex } from "./StandardFlex";
import { StandardTextField } from "./StandardTextField";
import { VoidCallback } from "./VoidCallback";

export interface StandardConditionalTextFieldProps {
    option1Text: string;
    option2Text: string;
    text: string;
    setText: VoidCallback<string>;
}

export const StandardConditionalTextField: FC<StandardConditionalTextFieldProps> = ({ option1Text, option2Text, text, setText }) => {
    const [isOption1Chosen, setIsOption1Chosen] = useState<boolean>(true);

    const isTextFieldEnabled = isOption1Chosen;

    const setIsOption1ChosenWrapped = (value: boolean) => {
        setText("");
        setIsOption1Chosen(value);
    }

    return <StandardFlex>
        <BooleanToggle
            option1Text={option1Text}
            option2Text={option2Text}
            isOption1Chosen={isOption1Chosen}
            setIsOption1Chosen={setIsOption1ChosenWrapped}
        />
        <StandardTextField text={text} setText={setText} disabled={!isTextFieldEnabled} />
    </StandardFlex>;
}