package pl.michal_sobiech.shared.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import pl.michal_sobiech.engineering_thesis.utils.LocalDateTimeWindow;
import pl.michal_sobiech.engineering_thesis.utils.OffsetDateTimeWindow;
import pl.michal_sobiech.engineering_thesis.utils.ZonedDate;

public class DateUtils {

    public static ZonedDateTime createZonedDateTime(ZonedDate date, LocalTime time) {
        return ZonedDateTime.of(date.date(), time, date.timeZone());
    }

    public static List<LocalDateTimeWindow> sortByStartAscending(List<LocalDateTimeWindow> windows) {
        return windows.stream().sorted(Comparator.comparing(LocalDateTimeWindow::start)).collect(Collectors.toList());
    }

    // Returned list is sorted in ascending order
    public static List<LocalDateTimeWindow> mergeTimeWindows(List<LocalDateTimeWindow> windows) {
        if (windows.size() == 0) {
            return windows;
        }

        List<LocalDateTimeWindow> sortedWindows = sortByStartAscending(windows);

        List<LocalDateTimeWindow> mergedWindows = new ArrayList<>();

        LocalDateTimeWindow currentWindow = sortedWindows.get(0);
        for (int i = 1; i < windows.size(); i++) {
            LocalDateTimeWindow nextWindow = sortedWindows.get(i);
            boolean nextWindowOverlapsWithCurrent = nextWindow.start().isBefore(currentWindow.end());
            if (nextWindowOverlapsWithCurrent) {
                LocalDateTime newEnd = currentWindow.end().isAfter(nextWindow.end())
                        ? currentWindow.end()
                        : nextWindow.end();
                currentWindow = new LocalDateTimeWindow(
                        currentWindow.start(),
                        newEnd);
            } else {
                mergedWindows.add(currentWindow);
                currentWindow = nextWindow;
            }
        }

        mergedWindows.add(currentWindow);
        return mergedWindows;
    }

    public static List<LocalDateTimeWindow> subtractTimeWindow(List<LocalDateTimeWindow> from,
            LocalDateTimeWindow toSubtract) {
        List<LocalDateTimeWindow> merged = mergeTimeWindows(from);

        List<LocalDateTimeWindow> out = new ArrayList<>();
        for (var window : merged) {
            if (window.start().isAfter(toSubtract.end()) || window.end().isBefore(toSubtract.start())) {
                // No collision
                out.add(window);
                continue;
            }

            if (!window.start().isBefore(toSubtract.start())
                    && !window.end().isAfter(toSubtract.end())) {
                // window fully inside toSubtract, so omit window
                continue;
            }

            if (window.start().isBefore(toSubtract.start())
                    && window.end().isAfter(toSubtract.end())) {
                // toSubtract fully inside window, so cut out inside of window making it 2
                var window1 = new LocalDateTimeWindow(
                        window.start(),
                        toSubtract.start());
                var window2 = new LocalDateTimeWindow(
                        toSubtract.end(),
                        window.end());

                out.add(window1);
                out.add(window2);
                continue;
            }

            if (window.start().isBefore(toSubtract.start())
                    && !window.end().isBefore(toSubtract.start())
                    && !window.end().isAfter(toSubtract.end())) {
                // Collision on the left side of toSubtract, truncate window
                var truncated = new LocalDateTimeWindow(window.start(), toSubtract.start());
                out.add(truncated);
                continue;
            }

            if (!window.start().isBefore(toSubtract.start())
                    && !window.start().isAfter(toSubtract.end())
                    && window.end().isAfter(toSubtract.end())) {
                // Collision on the right side of toSubtract, truncate window
                var truncated = new LocalDateTimeWindow(toSubtract.end(), window.end());
                out.add(truncated);
                continue;
            }
        }
        return out;

    }

    public static List<LocalDateTimeWindow> subtractTimeWindowLists(List<LocalDateTimeWindow> from,
            List<LocalDateTimeWindow> windowsToSubtract) {
        List<LocalDateTimeWindow> out = from;
        for (var windowToSubtract : windowsToSubtract) {
            out = subtractTimeWindow(out, windowToSubtract);
        }
        return out;
    }

    public static List<LocalDate> getAllDatesBetweenIncludingBorders(LocalDate from, LocalDate to) {
        return from.datesUntil(to.plusDays(1)).collect(Collectors.toList());
    }

    public static OffsetDateTime createOffsetDateTime(LocalDate date, ZoneId timeZone) {
        return date.atStartOfDay(timeZone).toOffsetDateTime();
    }

    public static OffsetDateTime createOffsetDateTime(LocalDateTime date, ZoneId timeZone) {
        return date.atZone(timeZone).toOffsetDateTime();
    }

    public static OffsetDateTime createOffsetDateTimeWithResetTime(OffsetDateTime datetime) {
        return datetime.withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    public static LocalDateTime createLocalDateTime(OffsetDateTime offsetDatetime, ZoneId timezone) {
        return offsetDatetime.atZoneSameInstant(timezone).toLocalDateTime();
    }

    public static LocalDateTime createLocalDateTime(Instant instant, ZoneId timezone) {
        return instant.atZone(timezone).toLocalDateTime();
    }

    public static LocalDate createLocalDate(OffsetDateTime offsetDatetime, ZoneId timezone) {
        LocalDateTime datetime = createLocalDateTime(offsetDatetime, timezone);
        return datetime.toLocalDate();
    }

    public static List<LocalDateTimeWindow> filterWindowsFullyContainedInRange(List<LocalDateTimeWindow> windows,
            LocalDateTime from, LocalDateTime to) {
        return windows.stream()
                .filter(window -> !window.start().isBefore(from) && !window.end().isAfter(to))
                .collect(Collectors.toList());
    }

    public static String extractHHmmTimeFromLocalDateTime(LocalDateTime datetime) {
        return datetime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public static OffsetDateTimeWindow createOffsetDateTimeWindow(LocalDateTimeWindow localWindow, ZoneId timezone) {
        return new OffsetDateTimeWindow(
                createOffsetDateTime(localWindow.start(), timezone),
                createOffsetDateTime(localWindow.end(), timezone));
    }

    public static Instant createInstant(LocalDateTime localDatetime, ZoneId timezone) {
        return createOffsetDateTime(localDatetime, timezone).toInstant();
    }

    public static String createIsoLocalDatetime(Instant instant, ZoneId timezone) {
        return instant.atZone(timezone).toLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

}
