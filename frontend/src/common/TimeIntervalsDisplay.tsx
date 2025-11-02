import { LocalTime } from "js-joda";
import { FC } from "react";
import { Calendar } from "react-big-calendar";
import { localizer } from "./localizer";

import { Box } from "@chakra-ui/react";
import "react-big-calendar/lib/css/react-big-calendar.css";
import { createNowWithTime } from "../utils/date";

export interface TimeIntervalsDisplayProps {
    intervals: [LocalTime, LocalTime][];
    resolutionMinutes: number;
}

export const TimeIntervalsDisplay: FC<TimeIntervalsDisplayProps> = ({ intervals, resolutionMinutes }) => {
    const events = intervals.map(interval => ({
        start: createNowWithTime(interval[0]),
        end: createNowWithTime(interval[1]),
    }))

    return <Box overflowY="scroll">
        <Calendar
            localizer={localizer}
            defaultView="day"
            views={["day"]}
            step={resolutionMinutes}
            toolbar={false}
            components={{
                header: ({ label }) => <span>{label.split(" ")[1]}</span>
            }}
            events={events}
        />
    </Box>
}