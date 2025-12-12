import { DayOfWeek, Duration, LocalTime } from "@js-joda/core";
import { FC, useState } from "react";
import { splitLocalTimePeriod } from "../../../../utils/date";
import { Position } from "../../../../utils/Position";
import { StateUpdater } from "../../../../utils/StateUpdater";
import { WeeklyTimeWindow } from "../../../../utils/WeeklyTimeWindow";
import { WeeklyTimeWindowWithCapacity } from "../../../../utils/WeeklyTimeWindowWithCapacity";
import { EditableNonCustomWeeklyCalendarPopup } from "./EditableNonCustomWeeklyCalendarPopup";
import { EditableWeeklySchedule } from "./EditableWeeklySchedule";

export interface EditableNonCustomWeeklyScheduleProps {
    appointmentDurationMinutes: number | null
    events: WeeklyTimeWindowWithCapacity[];
    setEvents: StateUpdater<WeeklyTimeWindowWithCapacity[]>;
}

export const EditableNonCustomWeeklySchedule: FC<EditableNonCustomWeeklyScheduleProps> = ({ appointmentDurationMinutes, events, setEvents }) => {
    const [lastClickPos, setLastClickPos] = useState<Position | null>(null);
    const [selectedEvent, setSelectedEvent] = useState<WeeklyTimeWindowWithCapacity | null>(null);

    const onSelectSlot = (slot: WeeklyTimeWindow) => {
        const duration = appointmentDurationMinutes === null
            ? null
            : Duration.ofMinutes(appointmentDurationMinutes);

        let windows: WeeklyTimeWindow[];
        if (duration === null) {
            windows = [slot];
        } else {
            windows = splitLocalTimePeriod(slot.start, slot.end, duration)
                .map(window => ({
                    dayOfWeek: slot.dayOfWeek,
                    start: window[0],
                    end: window[1]
                }));
        }

        const windowsWithCapacity: WeeklyTimeWindowWithCapacity[] = windows.map(window => ({
            ...window,
            capacity: 1,
        }));

        setEvents(prevEvents => [...prevEvents, ...windowsWithCapacity]);
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

    let popup;
    if (selectedEvent === null) {
        popup = null;
    } else {
        const setCapacity = (value: number) => {
            const updatedEvent = { ...selectedEvent };
            updatedEvent.capacity = value;
            setSelectedEvent(updatedEvent);
        };

        const removeEvent = () => {
            deleteEvent(selectedEvent.dayOfWeek, selectedEvent.start, selectedEvent.end);
        }
        return <EditableNonCustomWeeklyCalendarPopup
            close={() => setSelectedEvent(null)}
            remove={removeEvent}
            position={lastClickPos ?? { x: 0, y: 0 }}
            capacity={selectedEvent.capacity}
            setCapacity={setCapacity}
        />;
    }

    return <>
        <EditableWeeklySchedule
            timeWindows={events}
            onSelectSlot={onSelectSlot}
            onSelectEvent={onSelectEvent}
        />
        {popup}
    </>
};
