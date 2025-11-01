import { FC } from "react";
import { Calendar, SlotInfo } from "react-big-calendar";
import "react-big-calendar/lib/css/react-big-calendar.css";
import { UseStateSetter } from "../../utils/useState";
import { localizer } from "../localizer";
import { EventWithId } from "./EventWithId";

export interface WeeklyCalendarProps {
    events: EventWithId[];
    setEvents: UseStateSetter<EventWithId[]>;
    onSelectSlot?: (slot: SlotInfo) => void;
}

export const WeeklyCalendar: FC<WeeklyCalendarProps> = ({ events, setEvents, onSelectSlot }) => {
    const defaultOnSelectSlot = (slot: SlotInfo) => {
        const event: EventWithId = {
            start: slot.start,
            end: slot.end,
            resource: { tempId: crypto.randomUUID() }
        }
        setEvents([...events, event]);
    }
    onSelectSlot = onSelectSlot ?? defaultOnSelectSlot;

    function deleteEvent(eventToDelete: EventWithId) {
        setEvents(previousEvents => previousEvents.filter(event => {
            return event.resource.tempId != eventToDelete.resource.tempId;
        }));
    }

    const onSelectEvent = (event: EventWithId) => {
        deleteEvent(event);
    }

    return <Calendar
        localizer={localizer}
        events={events}
        views={["week"]}
        defaultView="week"
        selectable
        toolbar={false}
        onSelectSlot={onSelectSlot}
        components={{
            header: ({ label }) => <span>{label.split(" ")[1]}</span>
        }}
        onSelectEvent={onSelectEvent}
    />;
}