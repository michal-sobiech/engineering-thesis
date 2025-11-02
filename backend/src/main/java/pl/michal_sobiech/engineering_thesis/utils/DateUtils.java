package pl.michal_sobiech.engineering_thesis.utils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;

public class DateUtils {

    public static ZonedDateTime fromZonedDateAndLocalTime(ZonedDate date, LocalTime time) {
        return ZonedDateTime.of(date.date(), time, date.timeZone());
    }

    public static List<LocalDateTimeWindow> sortByStartAscending(List<LocalDateTimeWindow> windows) {
        return windows.stream().sorted(Comparator.comparing(LocalDateTimeWindow::start)).toList();
    }

    // Returned list is sorted in ascending order
    public static List<LocalDateTimeWindow> mergeTimeWindows(List<LocalDateTimeWindow> windows) {
        if (windows.size() == 0) {
            return windows;
        }

        List<LocalDateTimeWindow> sortedWindows = sortByStartAscending(windows);

        List<LocalDateTimeWindow> mergedWindows = List.of();

        LocalDateTimeWindow currentWindow = sortedWindows.get(0);
        for (int i = 1; i < windows.size(); i++) {
            LocalDateTimeWindow nextWindow = sortedWindows.get(i);
            boolean nextWindowOverlapsWithCurrent = !nextWindow.start().isAfter(currentWindow.end());
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

        List<LocalDateTimeWindow> out = List.of();
        for (var window : merged) {
            if (window.start().isAfter(toSubtract.end()) || window.end().isBefore(toSubtract.start())) {
                // No collision
                out.add(window);
                continue;
            }

            if (window.start().isBefore(toSubtract.end())) {
                if (window.end().isBefore(toSubtract.end())) {
                    // window is completely omitted
                    continue;
                } else {
                    var cutWindow = new LocalDateTimeWindow(toSubtract.end(), window.end());
                    out.add(cutWindow);
                    continue;
                }
            }

            if (window.end().isAfter(toSubtract.start())) {
                if (window.start().isAfter(toSubtract.start())) {
                    // window is completely omitted
                    continue;
                } else {
                    var cutWindow = new LocalDateTimeWindow(window.start(), toSubtract.start());
                    out.add(cutWindow);
                    continue;
                }
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

}
