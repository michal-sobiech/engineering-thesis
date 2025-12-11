import { DayOfWeek } from "@js-joda/core";
import { reverseMap } from "./map";

// US number of day of week (0 is Sunday) to DayOfWeek
const usDayOfWeekToJodaMap = new Map<number, DayOfWeek>([
    [0, DayOfWeek.SUNDAY],
    [1, DayOfWeek.MONDAY],
    [2, DayOfWeek.TUESDAY],
    [3, DayOfWeek.WEDNESDAY],
    [4, DayOfWeek.THURSDAY],
    [5, DayOfWeek.FRIDAY],
    [6, DayOfWeek.SATURDAY],
]);

const jodaDayOfWeekToUsMap: Map<DayOfWeek, number> = reverseMap(usDayOfWeekToJodaMap);

export function usDayOfWeekToJoda(usDayOfWeek: number): DayOfWeek {
    const jodaDayOfWeek = usDayOfWeekToJodaMap.get(usDayOfWeek);
    if (jodaDayOfWeek !== undefined) {
        return jodaDayOfWeek
    }
    throw new Error("Number of day of week is outside the range 0-6");
}

export function jodaDayOfWeekToUs(jodaDayOfWeek: DayOfWeek): number {
    const usDayOfWeek = jodaDayOfWeekToUsMap.get(jodaDayOfWeek);
    if (usDayOfWeek !== undefined) {
        return usDayOfWeek;
    }
    throw new Error("No such key");
}