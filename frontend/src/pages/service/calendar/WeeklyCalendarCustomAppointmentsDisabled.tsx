import { Duration, format, getDay, intervalToDuration, parse, startOfWeek } from "date-fns";
import { enUS } from "date-fns/locale";
import { err, ok, Result } from "neverthrow";
import { FC, JSX, useState } from "react";
import { Calendar, dateFnsLocalizer, SlotInfo } from "react-big-calendar";
import { EventWithIdAndCapacity } from "../../../common/calendar/EventWithIdAndCapacity";
import { getLastFromArray } from "../../../utils/array";
import { durationToMs, splitPeriod } from "../../../utils/date";
import { Position } from "../../../utils/Position";
import { UseStateSetter } from "../../../utils/useState";
import { WeeklyCalendarCustomAppoinmentsDisabledPopup } from "./WeeklyCalendarCustomAppoinmentsDisabledPopup";

const locales = { "en-US": enUS };
const localizer = dateFnsLocalizer({
    format,
    parse,
    getDay,
    locales,
    startOfWeek: (date: Date) => startOfWeek(date, { weekStartsOn: 1 })
});

export interface WeeklyCalendarCustomAppoinmentsDisabledProps {
    events: EventWithIdAndCapacity[];
    setEvents: UseStateSetter<EventWithIdAndCapacity[]>;
    eventDuration: Duration;
}

export const WeeklyCalendarCustomAppoinmentsDisabled: FC<WeeklyCalendarCustomAppoinmentsDisabledProps> = ({ events, setEvents, eventDuration }) => {
    const [lastClickPos, setLastClickPos] = useState<Position | null>(null);
    const [selectedEventId, setSelectedEventId] = useState<string | null>(null);

    const onSelectSlot = (slot: SlotInfo) => {
        const intervals = splitPeriodIntoIntervals(slot.start, slot.end, eventDuration);

        const events: EventWithIdAndCapacity[] = intervals.map(interval => ({
            start: interval[0],
            end: interval[1],
            resource: {
                tempId: crypto.randomUUID(),
                capacity: 1,
            }
        }));

        setEvents(previousEvents => [...previousEvents, ...events]);
    }

    function deleteEvent(eventId: string) {
        setEvents(previousEvents => previousEvents.filter(event => {
            return event.resource.tempId != eventId;
        }));
    }

    const onSelectEvent = (event: EventWithIdAndCapacity, e: React.SyntheticEvent<HTMLElement>) => {
        const mouseEvent = e.nativeEvent as MouseEvent;
        setLastClickPos({ x: mouseEvent.clientX, y: mouseEvent.clientY });
        setSelectedEventId(event.resource.tempId);
    }

    function findEventById(eventId: string): Result<EventWithIdAndCapacity, Error> {
        const event: EventWithIdAndCapacity | undefined = events.find(event => event.resource.tempId == eventId);
        return event === undefined
            ? err(new Error("No event with such id"))
            : ok(event);
    }

    let popup: JSX.Element | null;
    if (selectedEventId !== null) {
        const selectedEventResult = findEventById(selectedEventId);
        if (selectedEventResult.isErr()) {
            popup = null;
        } else {
            const selectedEvent = selectedEventResult.value;
            const capacity = selectedEvent.resource.capacity;
            const setCapacity = (newCapacity: number) => {
                setEvents(prevEvents => prevEvents.map(event => {
                    if (event.resource.tempId === selectedEvent.resource.tempId) {
                        event.resource.capacity = newCapacity;
                    }
                    return event;
                }))
            };

            popup = <WeeklyCalendarCustomAppoinmentsDisabledPopup
                close={() => setSelectedEventId(null)}
                remove={() => deleteEvent(selectedEventId)}
                position={lastClickPos ?? { x: 0, y: 0 }}
                capacity={capacity}
                setCapacity={setCapacity}
            />;
        }
    } else {
        popup = null;
    }

    return <>
        <Calendar
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
        />
        {popup}
    </>
}

function splitPeriodIntoIntervals(periodStart: Date, periodEnd: Date, eventDuration: Duration): [Date, Date][] {
    const intervals = splitPeriod(periodStart, periodEnd, eventDuration);
    const lastInterval = getLastFromArray(intervals)
    if (lastInterval !== undefined) {
        const slotLength = intervalToDuration({ start: lastInterval[0], end: lastInterval[1] })
        if (durationToMs(slotLength) < durationToMs(eventDuration)) {
            intervals.pop();
        }
    }
    return intervals;
}
