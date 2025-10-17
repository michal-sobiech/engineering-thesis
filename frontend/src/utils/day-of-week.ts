import { err, ok, Result } from "neverthrow";
import { DayOfWeek } from "../GENERATED-api";

// US number of day of week (0 is Sunday) to DayOfWeek
export function usDayOfWeekToDayOfWeek(usDayOfWeek: number): Result<DayOfWeek, Error> {
    switch (usDayOfWeek) {
        case 0: return ok("SUNDAY");
        case 1: return ok("MONDAY");
        case 2: return ok("TUESDAY");
        case 3: return ok("WEDNESDAY");
        case 4: return ok("THURSDAY");
        case 5: return ok("FRIDAY");
        case 6: return ok("SATURDAY");
        default: return err(new Error("Number of day of week is outside the range 0-6"));
    }
}