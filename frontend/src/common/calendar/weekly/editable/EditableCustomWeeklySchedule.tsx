import { DayOfWeek, LocalTime } from "@js-joda/core";
import { FC } from "react";
import { StateUpdater } from "../../../../utils/StateUpdater";
import { WeeklyTimeWindow } from "../../../../utils/WeeklyTimeWindow";
import { EditableWeeklySchedule } from "./EditableWeeklySchedule";

export interface EditableCustomWeeklyScheduleProps {
    events: WeeklyTimeWindow[];
    setEvents: StateUpdater<WeeklyTimeWindow[]>;
}

export const EditableCustomWeeklySchedule: FC<EditableCustomWeeklyScheduleProps> = ({ events, setEvents }) => {
    const onSelectSlot = (slot: WeeklyTimeWindow) => {
        setEvents(prevEvents => [...prevEvents, slot]);
    };

    const onSelectEvent = (dayOfWeek: DayOfWeek, start: LocalTime, end: LocalTime) => {
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

    return <EditableWeeklySchedule
        timeWindows={events}
        onSelectSlot={onSelectSlot}
        onSelectEvent={onSelectEvent}
    />;
}