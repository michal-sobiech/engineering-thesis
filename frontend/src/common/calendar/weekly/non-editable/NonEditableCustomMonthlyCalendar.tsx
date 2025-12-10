import { Calendar, SlotInfo } from "react-big-calendar";
import { localizer } from "../../../localizer";

import { Box } from "@chakra-ui/react";
import { LocalDate } from "@js-joda/core";
import { FC } from "react";
import "react-big-calendar/lib/css/react-big-calendar.css";
import { extractLocalDateFromDate } from "../../../../utils/date";

export interface NonEditableCustomMonthlyCalendar {
    selectedDate: LocalDate | null;
    setSelectedDate: (value: LocalDate | null) => void;
    onSelectSlot: (slot: SlotInfo) => void;
}

export const NonEditableCustomMonthlyCalendar: FC<NonEditableCustomMonthlyCalendar> = ({ selectedDate, setSelectedDate, onSelectSlot: externalOnSelectSlot }) => {
    const onSelectSlot = async (slot: SlotInfo) => {
        const slotDate = slot.start;
        const slotLocalDate = extractLocalDateFromDate(slotDate);
        setSelectedDate(slotLocalDate);
        externalOnSelectSlot(slot);
    }

    const dayPropGetter = (date: Date) => {
        const localDate = extractLocalDateFromDate(date);
        return {
            style: {
                backgroundColor:
                    selectedDate?.equals(localDate)
                        ? "#6284ebff"
                        : undefined
            }
        }
    }

    return <Box height="100%">
        <Calendar
            localizer={localizer}
            views={["month"]}
            defaultView="month"
            selectable
            onSelectSlot={onSelectSlot}
            style={{ height: "100%" }}
            dayPropGetter={dayPropGetter}
        />
    </Box>;
}
