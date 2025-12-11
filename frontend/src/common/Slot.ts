import { DayOfWeek, LocalTime } from "@js-joda/core";

export interface Slot {
    dayOfWeek: DayOfWeek;
    startTime: LocalTime;
    endTime: LocalTime;
    maxOccupancy: number;
}