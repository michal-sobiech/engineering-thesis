import { DayOfWeek, LocalTime } from "@js-joda/core";

export interface TimeWindow {
    dayOfWeek: DayOfWeek;
    startTime: LocalTime;
    endTime: LocalTime;
}