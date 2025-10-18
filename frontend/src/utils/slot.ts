import { err, ok, Result } from "neverthrow";
import { EventWithId } from "../common/calendar/EventWithId";
import { Slot } from "../GENERATED-api";
import { extractHHmmTimeFromDate } from "./date";
import { usDayOfWeekToDayOfWeek } from "./day-of-week";

export function eventWithIdToSlot(event: EventWithId): Result<Slot, Error> {
    if (event.start.getDay() !== event.end.getDay()) {
        return err(new Error("Event should start and end on the same day"));
    }

    const dayOfWeek = usDayOfWeekToDayOfWeek(event.start.getDay());
    if (dayOfWeek.isErr()) {
        return err(dayOfWeek.error);
    }

    const startHour = extractHHmmTimeFromDate(event.start);
    const endHour = extractHHmmTimeFromDate(event.end);

    return ok({
        dayOfWeek: dayOfWeek.value,
        start: startHour,
        end: endHour,
    });
}