import { Flex, Input, Text } from "@chakra-ui/react";
import { FC } from "react";
import DatePicker from "react-datepicker";
import { UseStateSetter } from "../utils/useState";

export interface StandardDateRangePickerProps {
    date1: Date;
    setDate1: UseStateSetter<Date>;
    date2: Date;
    setDate2: UseStateSetter<Date>;
}

export const StandardDateRangePicker: FC<StandardDateRangePickerProps> = ({ date1, setDate1, date2, setDate2 }) => {
    const onPicker1Select = (newDate1: Date | null) => {
        if (newDate1 === null) {
            return;
        }
        setDate1(newDate1);
        if (newDate1 > date2) {
            setDate2(newDate1);
        }
    }

    const onPicker2Select = (newDate2: Date | null) => {
        if (newDate2 === null) {
            return;
        }
        if (newDate2 > date1) {
            setDate2(newDate2);
        } else {
            setDate2(date1);
        }
    }

    return <Flex direction="row">
        <Text>
            From:
        </Text>
        <DatePicker
            selected={date1}
            onSelect={onPicker1Select}
            showTimeSelect
            customInput={<Input />}
        />
        <Text>to:</Text>
        <DatePicker
            selected={date2}
            onSelect={onPicker2Select}
            showTimeSelect
            customInput={<Input />}
        />
    </Flex>
}