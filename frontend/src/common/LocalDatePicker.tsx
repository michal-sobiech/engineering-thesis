import { Input } from "@chakra-ui/react";
import { LocalDate } from "@js-joda/core";
import { FC } from "react";
import DatePicker from "react-datepicker";
import { UseStateSetter } from "../utils/use-state";

export interface LocalDatePickerProps {
    date: LocalDate | null;
    setDate: UseStateSetter<LocalDate | null>;
}

export const LocalDatePicker: FC<LocalDatePickerProps> = ({ date, setDate }) => {
    const jsDate = date === null
        ? null
        : new Date(
            date.year(),
            date.monthValue() - 1,
            date.dayOfMonth()
        );

    const onChange = (newDate: Date | null) => {
        if (newDate === null) {
            setDate(null);
        } else {
            const newDateLocal = LocalDate.of(
                newDate.getFullYear(),
                newDate.getMonth(),
                newDate.getDate()
            )
            setDate(newDateLocal);
        }
    }

    return <DatePicker
        selected={jsDate}
        onChange={onChange}
        showTimeSelect
        dateFormat="yyyy-MM-dd"
        customInput={<Input />}
    />;
}