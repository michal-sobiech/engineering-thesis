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

export function extractFormattedTimeFromDate(date: Date): string {
    return date.toISOString().substring(11, 19);
}