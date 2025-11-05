import { DateTimeFormatter, Instant, LocalDate, LocalDateTime, LocalTime, ZonedDateTime, ZoneId, ZoneOffset } from "@js-joda/core";
import { add, Duration } from "date-fns";

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