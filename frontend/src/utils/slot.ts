import { LocalTime } from "@js-joda/core";
import { EventWithIdAndCapacity } from "../common/calendar/EventWithCapacity";
import { Slot } from "../common/Slot";
import { extractHHmmTimeFromDate } from "./date";
import { usDayOfWeekToJoda } from "./day-of-week";

export function eventWithIdAndCapacityToSlot(event: EventWithIdAndCapacity): Slot {
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
        maxOccupancy: event.resource.capacity,
    };
}