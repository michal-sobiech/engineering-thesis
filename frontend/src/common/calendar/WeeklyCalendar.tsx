import { format, getDay, parse, startOfWeek } from "date-fns";
import { enUS } from "date-fns/locale";
import { FC } from "react";
import { Calendar, dateFnsLocalizer, SlotInfo } from "react-big-calendar";
import "react-big-calendar/lib/css/react-big-calendar.css";
import { VoidCallback } from "../VoidCallback";
import { EventWithId } from "./EventWithId";

const locales = { "en-US": enUS };
const localizer = dateFnsLocalizer({
    format,
    parse,
    getDay,
    locales,
    startOfWeek: (date: Date) => startOfWeek(date, { weekStartsOn: 1 })
});

export interface WeeklyCalendarProps {
    events: EventWithId[];
    setEvents: VoidCallback<(previousEvents: EventWithId[]) => EventWithId[]>;
    onSelectSlot?: (slot: SlotInfo) => void;
}

export const WeeklyCalendar: FC<WeeklyCalendarProps> = ({ events, setEvents, onSelectSlot }) => {
    const defaultOnSelectSlot = (slot: SlotInfo) => {
        console.log(slot);
        const event: EventWithId = {
            start: slot.start,
            end: slot.end,
            resource: { tempId: crypto.randomUUID() }
        }
        setEvents(previousEvents => [...previousEvents, event]);
    }
    onSelectSlot = onSelectSlot ?? defaultOnSelectSlot;

    const onSelectEvent = (eventToDelete: EventWithId) => {
        setEvents(previousEvents => previousEvents.filter(event => {
            return event.resource.tempId != eventToDelete.resource.tempId;
        }));
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