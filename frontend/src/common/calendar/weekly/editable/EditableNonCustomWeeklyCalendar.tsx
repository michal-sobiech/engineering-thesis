import { FC, useState } from "react";
import { SlotInfo } from "react-big-calendar";
import { doesDateTimeWindowOverlapWithGroup, splitPeriod } from "../../../../utils/date";
import { Position } from "../../../../utils/Position";
import { StateUpdater } from "../../../../utils/StateUpdater";
import { EventWithIdAndCapacity } from "../../EventWithIdAndCapacity";
import { WeeklyCalendar } from "../WeeklyCalendar";
import { EditableNonCustomWeeklyCalendarPopup } from "./EditableNonCustomWeeklyCalendarPopup";

export interface EditableNonCustomWeeklyCalendarProps {
    appointmentDurationMinutes: number | null
    events: EventWithIdAndCapacity[];
    setEvents: StateUpdater<EventWithIdAndCapacity[]>;
}

export const EditableNonCustomWeeklyCalendar: FC<EditableNonCustomWeeklyCalendarProps> = ({ appointmentDurationMinutes, events, setEvents }) => {
    const [lastClickPos, setLastClickPos] = useState<Position | null>(null);
    const [selectedEvent, setSelectedEvent] = useState<EventWithIdAndCapacity | null>(null);

    const onSelectSlot = (slotInfo: SlotInfo) => {
        if (doesSlotOverlapWithEvents(slotInfo, events)) {
            return;
        }

        const subslotWindows: [Date, Date][] = appointmentDurationMinutes === null
            ? [[slotInfo.start, slotInfo.end]]
            : splitPeriod(slotInfo.start, slotInfo.end, { minutes: appointmentDurationMinutes });

        const newEvents: EventWithIdAndCapacity[] = subslotWindows.map(window => ({
            start: window[0],
            end: window[1],
            resource: {
                instanceId: crypto.randomUUID(),
                capacity: 1,
            }
        }));

        setEvents(prevEvents => [...prevEvents, ...newEvents]);
    };

    const onSelectEvent = (event: EventWithIdAndCapacity) => {
        setSelectedEvent(event);
    }

    function deleteEvent(eventInstanceId: string) {
        setEvents(previousEvents => previousEvents.filter(event => {
            return event.resource.instanceId != eventInstanceId;
        }));
    }

    let popup;
    if (selectedEvent === null) {
        popup = null;
    } else {
        const setCapacity = (value: number) => {
            const updatedEvent = { ...selectedEvent };
            updatedEvent.resource.capacity = value;
            setSelectedEvent(updatedEvent);
        };
        return <EditableNonCustomWeeklyCalendarPopup
            close={() => setSelectedEvent(null)}
            remove={() => deleteEvent(selectedEvent.resource.instanceId)}
            position={lastClickPos ?? { x: 0, y: 0 }}
            capacity={selectedEvent.resource.capacity}
            setCapacity={setCapacity}
        />;
    }

    return <>
        <WeeklyCalendar<EventWithIdAndCapacity>
            onSelectSlot={onSelectSlot}
            onSelectEvent={onSelectEvent}
        />
        {popup}
    </>
}

function doesSlotOverlapWithEvents(slotInfo: SlotInfo, events: EventWithIdAndCapacity[]): boolean {
    const slotWindow: [Date, Date] = [slotInfo.start, slotInfo.end];
    const eventWindows: [Date, Date][] = events.map(event => [event.start, event.end]);
    return doesDateTimeWindowOverlapWithGroup(slotWindow, eventWindows);
}