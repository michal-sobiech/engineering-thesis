import { LocalTime } from "@js-joda/core";
import { createEventWithId, EventWithId } from "../common/calendar/Event";
import { TimeWindow } from "../common/TimeWindow";
import { createDateWithSetTime, extractHHmmTimeFromDate, getExampleDateWithDayOfWeek } from "./date";
import { usDayOfWeekToJoda } from "./day-of-week";

export function eventWithIdToTimeWindow(event: EventWithId): TimeWindow {
    if (event.start.getDay() !== event.end.getDay()) {
        throw new Error("Event should start and end on the same day");
    }

    const dayOfWeek = usDayOfWeekToJoda(event.start.getDay());

    const startHour = extractHHmmTimeFromDate(event.start);
    const endHour = extractHHmmTimeFromDate(event.end);

    return {
        dayOfWeek,
        startTime: LocalTime.parse(startHour),
        endTime: LocalTime.parse(endHour),
    };
}

export function timeWindowToEventWithId(timeWindow: TimeWindow): EventWithId {
    const date = getExampleDateWithDayOfWeek(timeWindow.dayOfWeek);
    const start = createDateWithSetTime(date, timeWindow.startTime);
    const end = createDateWithSetTime(date, timeWindow.endTime);
    return createEventWithId(start, end);
}