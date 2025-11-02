import { Flex, Text } from "@chakra-ui/react";
import { LocalTime } from "js-joda";
import { FC } from "react";
import { LocalTimePicker } from "./LocalTimePicker";

export interface TimeRangePickerProps {
    time1: LocalTime | null;
    setTime1: (value: LocalTime | null) => void;
    time2: LocalTime | null;
    setTime2: (value: LocalTime | null) => void;
}

export const TimeRangePicker: FC<TimeRangePickerProps> = ({ time1, setTime1, time2, setTime2 }) => {
    const setTime1Wrapper = (newTime1: LocalTime | null) => {
        setTime1(newTime1);
        if (newTime1 === null) {
            setTime2(null);
            return;
        }
        if (time2 !== null && newTime1.isAfter(time2)) {
            setTime2(time1);
        }
    }

    const setTime2Wrapper = (newTime2: LocalTime | null) => {
        if (time1 !== null && newTime2?.isAfter(time1)) {
            setTime2(newTime2);
        }
        if (time1 !== null && newTime2?.isBefore(time1)) {
            setTime2(time1);
        }
    }

    return <Flex direction="row" align="center" gap="5px">
        <Text>From:</Text>
        <LocalTimePicker time={time1} setTime={setTime1Wrapper} />
        <Text>to:</Text>
        <LocalTimePicker time={time2} setTime={setTime2Wrapper} />
    </Flex>
}