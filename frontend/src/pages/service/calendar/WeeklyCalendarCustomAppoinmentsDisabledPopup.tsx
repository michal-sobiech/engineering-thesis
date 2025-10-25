import { Input } from "@chakra-ui/react";
import { FC } from "react";
import { NumericFormat } from "react-number-format";
import { StandardButton } from "../../../common/StandardButton";
import { StandardFlex } from "../../../common/StandardFlex";
import { StandardLabeledContainer } from "../../../common/StandardLabeledContainer";
import { StandardPanel } from "../../../common/StandardPanel";

export interface WeeklyCalendarCustomAppoinmentsDisabledPopupProps {
    close: () => void;
    remove: () => void;
    position: { x: number, y: number };
    capacity: number;
    setCapacity: (newCapacity: number) => void;
}

export const WeeklyCalendarCustomAppoinmentsDisabledPopup: FC<WeeklyCalendarCustomAppoinmentsDisabledPopupProps> = ({ close, remove, position, capacity, setCapacity }) => {
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
                onClick={() => remove()}
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