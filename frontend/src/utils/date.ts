import { add, Duration } from "date-fns";
import { Instant, LocalDate, LocalTime, ZonedDateTime, ZoneId } from "js-joda";

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
    return date.toISOString().substring(11, 16);
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