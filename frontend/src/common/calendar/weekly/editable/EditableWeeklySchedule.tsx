import { DayOfWeek, LocalTime } from "@js-joda/core";
import { FC } from "react";
import { SlotInfo } from "react-big-calendar";
import { WeeklyTimeWindow } from "../../../../utils/WeeklyTimeWindow";
import { createDateWithSetTime, doesWeeklyTimeWindowOverlapWithWeeklySchedule, extractJodaDayOfWeekFromJsDate, extractLocalTimeFromDate, getExampleDateWithDayOfWeek } from "../../../../utils/date";
import { Event } from "../../Event";
import { WeeklyCalendar } from "../WeeklyCalendar";

export interface EditableWeeklyScheduleProps {
    timeWindows: WeeklyTimeWindow[];
    onSelectSlot: (slot: WeeklyTimeWindow) => void;
    onSelectEvent: (dayOfWeek: DayOfWeek, start: LocalTime, end: LocalTime) => void;
}

export const EditableWeeklySchedule: FC<EditableWeeklyScheduleProps> = ({
    timeWindows,
    onSelectSlot: externalOnSelectSlot,
    onSelectEvent: externalOnSelectEvent,
}) => {
    const onSelectSlot = (slotInfo: SlotInfo) => {
        const slot: WeeklyTimeWindow = {
            dayOfWeek: extractJodaDayOfWeekFromJsDate(slotInfo.start),
            start: extractLocalTimeFromDate(slotInfo.start),
            end: extractLocalTimeFromDate(slotInfo.end),
        };

        if (doesWeeklyTimeWindowOverlapWithWeeklySchedule(slot, timeWindows)) {
            return;
        }

        externalOnSelectSlot(slot);
    };

    const onSelectEvent = (event: Event) => {
        const dayOfWeek = extractJodaDayOfWeekFromJsDate(event.start);
        const start = extractLocalTimeFromDate(event.start);
        const end = extractLocalTimeFromDate(event.end);
        externalOnSelectEvent(dayOfWeek, start, end);
    }

    const eventsForCalendar: Event[] = timeWindows.map(event => {
        const date = getExampleDateWithDayOfWeek(event.dayOfWeek);
        return {
            start: createDateWithSetTime(date, event.start),
            end: createDateWithSetTime(date, event.end),
        };
    });

    return <WeeklyCalendar<Event>
        events={eventsForCalendar}
        onSelectSlot={onSelectSlot}
        onSelectEvent={onSelectEvent}
    />;
}