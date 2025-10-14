import { Duration, intervalToDuration } from "date-fns";
import { FC } from "react";
import { SlotInfo } from "react-big-calendar";
import { getLast } from "../../utils/array";
import { durationToMs, splitPeriod } from "../../utils/date";
import { VoidCallback } from "../VoidCallback";
import { EventWithId } from "./EventWithId";
import { WeeklyCalendar } from "./WeeklyCalendar";

export interface WeeklyCalendarWithAutoDivideProps {
    events: EventWithId[];
    setEvents: VoidCallback<(previousEvents: EventWithId[]) => EventWithId[]>;
    eventDuration: Duration;
}

export const WeeklyCalendarWithAutoDivide: FC<WeeklyCalendarWithAutoDivideProps> = ({ events, setEvents, eventDuration }) => {
    const onSelectSlot = (slot: SlotInfo) => {
        console.log(slot);

        const intervals = splitPeriod(slot.start, slot.end, eventDuration);
        const lastInterval = getLast(intervals)
        if (lastInterval !== undefined) {
            const slotLength = intervalToDuration({ start: lastInterval[0], end: lastInterval[1] })
            if (durationToMs(slotLength) < durationToMs(eventDuration)) {
                intervals.pop();
            }
        }

        const events: EventWithId[] = intervals.map(interval => ({
            start: interval[0],
            end: interval[1],
            resource: { tempId: crypto.randomUUID() }
        }));

        setEvents(previousEvents => [...previousEvents, ...events]);
    }

    return <WeeklyCalendar {...{ events, setEvents, onSelectSlot }} />;
}