import { Input } from "@chakra-ui/react";
import { FC } from "react";
import { NumericFormat } from "react-number-format";
import { Position } from "../../../utils/Position";
import { StandardButton } from "../../StandardButton";
import { StandardFlex } from "../../StandardFlex";
import { StandardLabeledContainer } from "../../StandardLabeledContainer";
import { StandardPanel } from "../../StandardPanel";

export interface EditableNonCustomWeeklyCalendarPopupProps {
    close: () => void;
    remove: () => void;
    position: Position;
    capacity: number;
    setCapacity: (newCapacity: number) => void;
}

export const EditableNonCustomWeeklyCalendarPopup: FC<EditableNonCustomWeeklyCalendarPopupProps> = ({ close, remove, position, capacity, setCapacity }) => {
    return <StandardPanel
        position="fixed"
        left={position.x}
        top={position.y}
        zIndex={99999}
    >
        <StandardFlex>
            <StandardLabeledContainer label="Number of places">
                <NumericFormat
                    value={capacity}
                    onValueChange={newValue => setCapacity(newValue.floatValue ?? 1)}
                    allowNegative={false}
                    min={1}
                    decimalScale={0}
                    customInput={Input}
                />
            </StandardLabeledContainer>
            <StandardButton
                onClick={() => {
                    remove();
                    close();
                }}
                delayMs={0}
                width="100%"
                backgroundColor="primary.darkRed">
                Delete
            </StandardButton>
            <StandardButton
                onClick={() => close()}
                delayMs={0}
                width="100%">
                Ok
            </StandardButton>
        </StandardFlex>
    </StandardPanel >
}