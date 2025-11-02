import { Input } from "@chakra-ui/react";
import { LocalTime } from "js-joda";
import { FC } from "react";
import { UseStateSetter } from "../utils/useState";

export interface LocalTimePickerProps {
    time: LocalTime | null;
    setTime: UseStateSetter<LocalTime | null>;
    stepMinutes?: number;
}

const DEFAULT_STEP_MINUTES = 1;

export const LocalTimePicker: FC<LocalTimePickerProps> = ({ time, setTime, stepMinutes }) => {
    stepMinutes = stepMinutes ?? DEFAULT_STEP_MINUTES;

    const onChange: React.ChangeEventHandler<HTMLInputElement> = (event) => {
        const [hours, minutes] = event.target.value.split(":").map(Number);
        const newTime = LocalTime.of(hours, minutes);
        setTime(newTime);
    }

    const timeFormatted = time?.toString().slice(0, 5);

    const stepSeconds = stepMinutes * 60;

    return <Input
        type="time"
        value={timeFormatted}
        step={stepSeconds}
        onChange={onChange}
    />;
}