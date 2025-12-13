import { DayOfWeek, LocalTime } from "@js-joda/core";
import { FC } from "react";
import { StateUpdater } from "../../../../utils/StateUpdater";
import { WeeklyTimeWindow } from "../../../../utils/WeeklyTimeWindow";
import { EditableWeeklySchedule } from "./EditableWeeklySchedule";

export interface EditableCustomWeeklyScheduleProps {
    windows: WeeklyTimeWindow[];
    setWindows: StateUpdater<WeeklyTimeWindow[]>;
}

export const EditableCustomWeeklySchedule: FC<EditableCustomWeeklyScheduleProps> = ({ windows, setWindows }) => {
    const onSelectSlot = (slot: WeeklyTimeWindow) => {
        setWindows(prev => [...prev, slot]);
    };

    const onSelectEvent = (dayOfWeek: DayOfWeek, start: LocalTime, end: LocalTime) => {
        deleteEvent(dayOfWeek, start, end);
    }

    function deleteEvent(dayOfWeek: DayOfWeek, start: LocalTime, end: LocalTime) {
        setWindows(prev => prev.filter(window => {
            return (
                window.dayOfWeek === dayOfWeek
                && window.start === start
                && window.end === end
            );
        }));
    }

    return <EditableWeeklySchedule
        timeWindows={windows}
        onSelectSlot={onSelectSlot}
        onSelectEvent={onSelectEvent}
    />;
}