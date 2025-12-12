import { DayOfWeek, LocalTime } from "@js-joda/core";

export interface WeeklyTimeWindow {
    dayOfWeek: DayOfWeek,
    start: LocalTime,
    end: LocalTime,
}