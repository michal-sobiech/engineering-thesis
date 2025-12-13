package pl.michal_sobiech.core.utils.local_datetime_window;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LocalDatetimeWindowUtils {

    public static List<LocalDatetimeWindow> subtractTimeWindow(List<LocalDatetimeWindow> from,
            LocalDatetimeWindow toSubtract) {
        List<LocalDatetimeWindow> merged = mergeTimeWindows(from);

        List<LocalDatetimeWindow> out = new ArrayList<>();
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
                var window1 = new SimpleLocalDatetimeWindow(
                        window.start(),
                        toSubtract.start());
                var window2 = new SimpleLocalDatetimeWindow(
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
                var truncated = new SimpleLocalDatetimeWindow(window.start(), toSubtract.start());
                out.add(truncated);
                continue;
            }

            if (!window.start().isBefore(toSubtract.start())
                    && !window.start().isAfter(toSubtract.end())
                    && window.end().isAfter(toSubtract.end())) {
                // Collision on the right side of toSubtract, truncate window
                var truncated = new SimpleLocalDatetimeWindow(toSubtract.end(), window.end());
                out.add(truncated);
                continue;
            }
        }
        return out;

    }

    // Returned list is sorted in ascending order
    public static List<LocalDatetimeWindow> mergeTimeWindows(List<LocalDatetimeWindow> windows) {
        if (windows.size() == 0) {
            return windows;
        }

        List<LocalDatetimeWindow> sortedWindows = sortByStartAscending(windows);

        List<LocalDatetimeWindow> mergedWindows = new ArrayList<>();

        LocalDatetimeWindow currentWindow = sortedWindows.get(0);
        for (int i = 1; i < windows.size(); i++) {
            LocalDatetimeWindow nextWindow = sortedWindows.get(i);
            boolean nextWindowOverlapsWithCurrent = nextWindow.start().isBefore(currentWindow.end());
            if (nextWindowOverlapsWithCurrent) {
                LocalDateTime newEnd = currentWindow.end().isAfter(nextWindow.end())
                        ? currentWindow.end()
                        : nextWindow.end();
                currentWindow = new SimpleLocalDatetimeWindow(
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

    public static List<LocalDatetimeWindow> sortByStartAscending(List<LocalDatetimeWindow> windows) {
        return windows.stream().sorted(Comparator.comparing(LocalDatetimeWindow::start)).collect(Collectors.toList());
    }

    public static List<LocalDatetimeWindow> subtractTimeWindowLists(List<LocalDatetimeWindow> from,
            List<LocalDatetimeWindow> windowsToSubtract) {
        List<LocalDatetimeWindow> out = from;
        for (var windowToSubtract : windowsToSubtract) {
            out = subtractTimeWindow(out, windowToSubtract);
        }
        return out;
    }

    public static boolean doesPeriodCollideWithLocalDatetimeWindow(LocalDateTime periodStart, LocalDateTime periodEnd,
            LocalDatetimeWindow window) {
        return !window.start().isBefore(periodStart) && !window.end().isAfter(periodEnd);
    }

    public static <T extends LocalDatetimeWindow> List<T> filterWindowsFullyContainedInRange(
            List<T> windows,
            LocalDateTime from, LocalDateTime to) {
        return windows.stream()
                .filter(window -> doesPeriodCollideWithLocalDatetimeWindow(from, to, window))
                .collect(Collectors.toList());
    }

}
