import { Input } from "@chakra-ui/react";
import { FC } from "react";
import { NumericFormat } from "react-number-format";
import { StandardLabeledContainer } from "../../../common/StandardLabeledContainer";
import { StandardPanel } from "../../../common/StandardPanel";

export interface WeeklyCalendarCustomAppoinmentsDisabledPopupProps {
    close: () => void;
    position: { x: number, y: number };
    capacity: number;
    setCapacity: (newCapacity: number) => void;
}

export const WeeklyCalendarCustomAppoinmentsDisabledPopup: FC<WeeklyCalendarCustomAppoinmentsDisabledPopupProps> = ({ close, position, capacity, setCapacity }) => {
    return <StandardPanel
        onBlur={() => close()}
        position="absolute"
        left={position.x}
        top={position.y}
        zIndex={99999}
    >
        <StandardLabeledContainer label="Number of places">
            <NumericFormat
                value={capacity}
                onValueChange={newValue => setCapacity(newValue.floatValue ?? 0)}
                allowNegative={false}
                decimalScale={0}
                customInput={Input}
            />
        </StandardLabeledContainer>
    </StandardPanel >
}