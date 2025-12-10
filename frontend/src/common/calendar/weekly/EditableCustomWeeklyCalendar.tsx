import { FC } from "react";
import { SlotInfo } from "react-big-calendar";
import { doesDateTimeWindowOverlapWithGroup } from "../../../utils/date";
import { StateUpdater } from "../../../utils/StateUpdater";
import { EventWithId } from "../EventWithId";
import { WeeklyCalendar } from "./WeeklyCalendar";

export interface EditableCustomWeeklyCalendarProps {
    events: EventWithId[];
    setEvents: StateUpdater<EventWithId[]>;
}

export const EditableCustomWeeklyCalendar: FC<EditableCustomWeeklyCalendarProps> = ({ events, setEvents }) => {
    const onSelectSlot = (slotInfo: SlotInfo) => {
        if (doesSlotOverlapWithEvents(slotInfo, events)) {
            return;
        }

        const newEvent: EventWithId = {
            start: slotInfo.start,
            end: slotInfo.end,
            resource: {
                instanceId: crypto.randomUUID(),
            }
        };

        setEvents(prevEvents => [...prevEvents, newEvent]);
    };

    const onSelectEvent = (event: EventWithId) => {
        deleteEvent(event.resource.instanceId);
    }

    function deleteEvent(eventInstanceId: string) {
        setEvents(previousEvents => previousEvents.filter(event => {
            return event.resource.instanceId != eventInstanceId;
        }));
    }

    return <WeeklyCalendar<EventWithId>
        onSelectSlot={onSelectSlot}
        onSelectEvent={onSelectEvent}
    />;
}

function doesSlotOverlapWithEvents(slotInfo: SlotInfo, events: EventWithId[]): boolean {
    const slotWindow: [Date, Date] = [slotInfo.start, slotInfo.end];
    const eventWindows: [Date, Date][] = events.map(event => [event.start, event.end]);
    return doesDateTimeWindowOverlapWithGroup(slotWindow, eventWindows);
}