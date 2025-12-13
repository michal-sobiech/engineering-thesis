import { DayOfWeek, LocalTime } from "@js-joda/core";
import { FC } from "react";
import { SlotInfo } from "react-big-calendar";
import { WeeklyTimeWindow } from "../../../../utils/WeeklyTimeWindow";
import { calcDateWithDayOfWeekAndUsDateBase, createDateWithSetTime, createJsDateMovedByDays, doesWeeklyTimeWindowOverlapWithWeeklySchedule, EXAMPLE_SUNDAY_DATE, extractJodaDayOfWeekFromJsDate, extractSystemLocalTimeFromDate, } from "../../../../utils/date";
import { Event } from "../../Event";
import { WeeklyCalendar } from "../WeeklyCalendar";

export interface EditableWeeklyScheduleProps {
    timeWindows: WeeklyTimeWindow[];
    onSelectSlot: (slot: WeeklyTimeWindow) => void;
    onSelectEvent: (dayOfWeek: DayOfWeek, start: LocalTime, end: LocalTime) => void;
}

const INTERNAL_START_OF_US_WEEK: Date = EXAMPLE_SUNDAY_DATE;

export const EditableWeeklySchedule: FC<EditableWeeklyScheduleProps> = ({
    timeWindows,
    onSelectSlot: externalOnSelectSlot,
    onSelectEvent: externalOnSelectEvent,
}) => {
    const onSelectSlot = (slotInfo: SlotInfo) => {
        const slot: WeeklyTimeWindow = {
            dayOfWeek: extractJodaDayOfWeekFromJsDate(slotInfo.start),
            start: extractSystemLocalTimeFromDate(slotInfo.start),
            end: extractSystemLocalTimeFromDate(slotInfo.end),
        };

        if (doesWeeklyTimeWindowOverlapWithWeeklySchedule(slot, timeWindows)) {
            return;
        }

        externalOnSelectSlot(slot);
    };

    const onSelectEvent = (event: Event) => {
        const dayOfWeek = extractJodaDayOfWeekFromJsDate(event.start);
        const start = extractSystemLocalTimeFromDate(event.start);
        const end = extractSystemLocalTimeFromDate(event.end);
        externalOnSelectEvent(dayOfWeek, start, end);
    }

    const eventsForCalendar: Event[] = timeWindows.map(window => {
        const date = calcDateWithDayOfWeekAndUsDateBase(window.dayOfWeek, INTERNAL_START_OF_US_WEEK);
        return {
            start: createDateWithSetTime(date, window.start),
            end: createDateWithSetTime(date, window.end),
        };
    });

    const internal_start_of_iso_week = createJsDateMovedByDays(INTERNAL_START_OF_US_WEEK, 1);

    return <WeeklyCalendar<Event>
        date={internal_start_of_iso_week}
        events={eventsForCalendar}
        onSelectSlot={onSelectSlot}
        onSelectEvent={onSelectEvent}
    />;
}