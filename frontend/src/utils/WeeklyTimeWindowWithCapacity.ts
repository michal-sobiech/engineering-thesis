import { DayOfWeek, LocalTime } from "@js-joda/core";

export interface WeeklyTimeWindowWithCapacity {
    dayOfWeek: DayOfWeek,
    start: LocalTime,
    end: LocalTime,
    capacity: number;
}