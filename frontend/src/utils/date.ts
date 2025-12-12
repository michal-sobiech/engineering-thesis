import { DateTimeFormatter, DayOfWeek, Instant, LocalDate, LocalDateTime, LocalTime, ZonedDateTime, ZoneId, ZoneOffset } from "@js-joda/core";
import { add, Duration } from "date-fns";
import { jodaDayOfWeekToUs } from "./day-of-week";
import { WeeklyTimeWindow } from "./WeeklyTimeWindow";

export function splitPeriod(start: Date, end: Date, segmentDuration: Duration): [Date, Date][] {
    const out: [Date, Date][] = [];

    let currentSegmentStart = start;
    while (currentSegmentStart < end) {
        const currentSegmentEnd = add(currentSegmentStart, segmentDuration);
        if (currentSegmentEnd > end) {
            break;
        }
        const segment: [Date, Date] = [currentSegmentStart, currentSegmentEnd];
        out.push(segment);
        currentSegmentStart = currentSegmentEnd;
    }

    return out;
}

export function durationToMs(duration: Duration) {
    return add(new Date(0), duration).getTime();
}

export function extractHHmmTimeFromDate(date: Date): string {
    const hours = date.getHours();
    const minutes = date.getMinutes();

    console.log(date, hours);

    const hoursPadded = hours.toString().padStart(2, "0");
    const minutesPadded = minutes.toString().padStart(2, "0");
    return `${hoursPadded}:${minutesPadded}`;
}

export function createDateWithoutTime(date: Date): Date {
    const copy = new Date(date);
    date.setHours(0, 0, 0, 0);
    return date;
}

export function createNowWithTime(time: LocalTime): Date {
    const now = LocalDate.now();
    const zoneDate = ZonedDateTime.of(now, time, ZoneId.systemDefault());
    return new Date(zoneDate.toInstant().toEpochMilli());
}

export function extractLocalTimeFromDate(date: Date): LocalTime {
    return LocalTime.from(Instant.ofEpochMilli(date.getTime()));
}

export function createDateInterpretedAsUTC(date: Date): Date {
    return new Date(Date.UTC(
        date.getFullYear(),
        date.getMonth(),
        date.getDate(),
        date.getHours(),
        date.getMinutes(),
        date.getSeconds(),
        date.getMilliseconds()
    ));
}

export function createZonedDateTimeFromDateAndZoneId(date: Date, timezone: ZoneId): ZonedDateTime {
    const instant = Instant.ofEpochMilli(date.getTime());
    return ZonedDateTime.ofInstant(instant, timezone);
}

export function extractHHmmTimeFromLocalTime(time: LocalTime): string {
    return time.format(DateTimeFormatter.ofPattern("HH:mm"));
}

export function extractLocalDateFromDate(date: Date): LocalDate {
    return LocalDate.of(
        date.getFullYear(),
        date.getMonth() + 1,
        date.getDate()
    );
}

export function createUtcDateFromLocalDate(localDate: LocalDate): Date {
    return createUtcDateFromLocalDatetime(localDate.atStartOfDay());
}

export function createUtcDateFromLocalDatetime(localDatetime: LocalDateTime): Date {
    const utcInstant = localDatetime.toInstant(ZoneOffset.UTC);
    return new Date(utcInstant.toEpochMilli());
}

export function doInstantTimeWindowGroupsOverlap(group1: [Instant, Instant][], group2: [Instant, Instant][]): boolean {
    return group1.some(window1 =>
        group2.some(window2 =>
            doInstantTimeWindowsOverlap(window1, window2)
        ));
}

export function doInstantTimeWindowsOverlap(window1: [Instant, Instant], window2: [Instant, Instant]): boolean {
    const [start1, end1] = window1;
    const [start2, end2] = window2;
    return end2.isAfter(start1) && start2.isBefore(end1);
}

export function doDateTimeWindowsGroupsOverlap(group1: [Date, Date][], group2: [Date, Date][]): boolean {
    const group1Instant: [Instant, Instant][] = group1.map(dateWindowToInstantWindow);
    const group2Instant: [Instant, Instant][] = group2.map(dateWindowToInstantWindow);
    return doInstantTimeWindowGroupsOverlap(group1Instant, group2Instant);
}

export function doesInstantTimeWindowOverlapWithGroup(window: [Instant, Instant], group: [Instant, Instant][]): boolean {
    return doInstantTimeWindowGroupsOverlap([window], group);
}

export function doesDateTimeWindowsGroupHaveOverlap(windows: [Date, Date][]): boolean {
    const instantWindows = windows.map(dateWindowToInstantWindow);
    return doesInstantTimeWindowsGroupHaveOverlap(instantWindows);
}

export function doesInstantTimeWindowsGroupHaveOverlap(windows: [Instant, Instant][]): boolean {
    for (let i = 0; i < windows.length - 1; i++) {
        for (let k = i + 1; k < windows.length; k++) {
            if (doInstantTimeWindowsOverlap(windows[i], windows[k])) {
                return true;
            }
        }
    }
    return false;
}

export function dateWindowToInstantWindow(window: [Date, Date]): [Instant, Instant] {
    return [
        Instant.ofEpochMilli(window[0].getTime()),
        Instant.ofEpochMilli(window[1].getTime()),
    ];
}

export function doesDateTimeWindowOverlapWithGroup(window: [Date, Date], group: [Date, Date][]): boolean {
    return doesInstantTimeWindowOverlapWithGroup(
        dateWindowToInstantWindow(window),
        group.map(dateWindowToInstantWindow),
    );
}

export function getExampleDateWithDayOfWeek(dayOfWeek: DayOfWeek): Date {
    const exampleSundayDate: Date = new Date("2025-11-30");
    const dayOffset = jodaDayOfWeekToUs(dayOfWeek);
    return moveJsDateByDays(exampleSundayDate, dayOffset);
}

export function moveJsDateByDays(date: Date, numDays: number) {
    const newDate = new Date(date);
    newDate.setDate(date.getDate() + numDays);
    return newDate;
}

export function createDateWithSetTime(date: Date, time: LocalTime): Date {
    const newDate = new Date(date);
    const milliseconds = Math.floor(time.nano() / 1_000_000);
    newDate.setHours(time.hour(), time.minute(), time.second(), milliseconds);
    return newDate;
}

export function extractJodaDayOfWeekFromJsDate(jsDate: Date): DayOfWeek {
    const usDayOfWeek: number = jsDate.getDay();
    const isoDayOfWeek: number = usDayOfWeekToIso(usDayOfWeek);
    return DayOfWeek.of(isoDayOfWeek);
}

export function usDayOfWeekToIso(usDayOfWeek: number): number {
    return usDayOfWeek === 0
        ? 7
        : usDayOfWeek;
}

export function doLocalTimeWindowsOverlap(
    window1: [LocalTime, LocalTime], window2: [LocalTime, LocalTime]
): boolean {
    const [start1, end1] = window1;
    const [start2, end2] = window2;
    return end2.isAfter(start1) && start2.isBefore(end1);
}

export function doesLocalTimeWindowOverlapWithGroup(
    window: [LocalTime, LocalTime], group: [LocalTime, LocalTime][]
) {
    return group.some(windowFromGroup => doLocalTimeWindowsOverlap(windowFromGroup, window));
}

export function doesWeeklyTimeWindowOverlapWithWeeklySchedule(
    window: WeeklyTimeWindow, schedule: WeeklyTimeWindow[]
): boolean {
    const dayOfWeek = window.dayOfWeek;
    const scheduleOnDayOfWeek = schedule.filter(window => window.dayOfWeek === dayOfWeek);

    const localTimeWindow: [LocalTime, LocalTime] = [window.start, window.end];
    const scheduleOnDayOfWeekLocalTimeWindows: [LocalTime, LocalTime][] =
        scheduleOnDayOfWeek.map(window => [window.start, window.end]);
    return doesLocalTimeWindowOverlapWithGroup(localTimeWindow, scheduleOnDayOfWeekLocalTimeWindows);
}