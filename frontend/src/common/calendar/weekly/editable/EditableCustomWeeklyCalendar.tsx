import { DayOfWeek, LocalTime } from "@js-joda/core";
import { FC } from "react";
import { SlotInfo } from "react-big-calendar";
import { doesWeeklyTimeWindowOverlapWithWeeklySchedule, extractJodaDayOfWeekFromJsDate, extractLocalTimeFromDate } from "../../../../utils/date";
import { StateUpdater } from "../../../../utils/StateUpdater";
import { WeeklyTimeWindow } from "../../../../utils/WeeklyTimeWindow";
import { EventWithId } from "../../EventWithId";
import { WeeklyCalendar } from "../WeeklyCalendar";

export interface EditableCustomWeeklyCalendarProps {
    events: WeeklyTimeWindow[];
    setEvents: StateUpdater<WeeklyTimeWindow[]>;
}

export const EditableCustomWeeklyCalendar: FC<EditableCustomWeeklyCalendarProps> = ({ events, setEvents }) => {
    const onSelectSlot = (slotInfo: SlotInfo) => {

        const slotWeeklyTimeWindow: WeeklyTimeWindow = {
            dayOfWeek: extractJodaDayOfWeekFromJsDate(slotInfo.start),
            start: extractLocalTimeFromDate(slotInfo.start),
            end: extractLocalTimeFromDate(slotInfo.end),
        };

        if (doesWeeklyTimeWindowOverlapWithWeeklySchedule(slotWeeklyTimeWindow, events)) {
            return;
        }

        setEvents(prevEvents => [...prevEvents, slotWeeklyTimeWindow]);
    };

    const onSelectEvent = (event: EventWithId) => {
        const dayOfWeek = extractJodaDayOfWeekFromJsDate(event.start);
        const start = extractLocalTimeFromDate(event.start);
        const end = extractLocalTimeFromDate(event.end);
        deleteEvent(dayOfWeek, start, end);
    }

    function deleteEvent(dayOfWeek: DayOfWeek, start: LocalTime, end: LocalTime) {
        setEvents(previousEvents => previousEvents.filter(event => {
            return (
                event.dayOfWeek === dayOfWeek
                && event.start === start
                && event.end === end
            );
        }));
    }

    return <WeeklyCalendar<EventWithId>
        onSelectSlot={onSelectSlot}
        onSelectEvent={onSelectEvent}
    />;
}