import { DayOfWeek, LocalTime } from "@js-joda/core";
import { FC } from "react";
import { SlotInfo } from "react-big-calendar";
import { WeeklyTimeWindow } from "../../../../utils/WeeklyTimeWindow";
import { calcDateWithDayOfWeekAndIsoDateBase, createDateWithSetTime, createJsDateMovedByDays, doesWeeklyTimeWindowOverlapWithWeeklySchedule, EXAMPLE_MONDAY_DATE, extractJodaDayOfWeekFromJsDate, extractSystemLocalTimeFromDate } from "../../../../utils/date";
import { Event } from "../../Event";
import { WeeklyCalendar } from "../WeeklyCalendar";

export interface EditableWeeklyScheduleProps {
    timeWindows: WeeklyTimeWindow[];
    onSelectSlot: (slot: WeeklyTimeWindow) => void;
    onSelectEvent: (dayOfWeek: DayOfWeek, start: LocalTime, end: LocalTime, e: React.SyntheticEvent<HTMLElement>) => void;
}

const INTERNAL_START_OF_ISO_WEEK: Date = EXAMPLE_MONDAY_DATE;

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

    const onSelectEvent = (event: Event, e: React.SyntheticEvent<HTMLElement>) => {
        const dayOfWeek = extractJodaDayOfWeekFromJsDate(event.start);
        const start = extractSystemLocalTimeFromDate(event.start);
        const end = extractSystemLocalTimeFromDate(event.end);
        externalOnSelectEvent(dayOfWeek, start, end, e);
    }

    const eventsForCalendar: Event[] = timeWindows.map(window => {
        const date = calcDateWithDayOfWeekAndIsoDateBase(window.dayOfWeek, INTERNAL_START_OF_ISO_WEEK);
        return {
            start: createDateWithSetTime(date, window.start),
            end: createDateWithSetTime(date, window.end),
        };
    });

    const internal_start_of_iso_week = createJsDateMovedByDays(INTERNAL_START_OF_ISO_WEEK, 1);

    return <WeeklyCalendar<Event>
        date={internal_start_of_iso_week}
        events={eventsForCalendar}
        onSelectSlot={onSelectSlot}
        onSelectEvent={onSelectEvent}
    />;
}