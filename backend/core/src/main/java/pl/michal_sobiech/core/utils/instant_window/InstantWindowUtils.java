package pl.michal_sobiech.core.utils.instant_window;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class InstantWindowUtils {

    public static List<InstantWindow> subtractTimeWindowLists(List<InstantWindow> from,
            List<InstantWindow> windowsToSubtract) {
        List<InstantWindow> out = from;
        for (var windowToSubtract : windowsToSubtract) {
            out = subtractTimeWindow(out, windowToSubtract);
        }
        return out;
    }

    public static List<InstantWindow> subtractTimeWindow(List<InstantWindow> from,
            InstantWindow toSubtract) {
        List<InstantWindow> merged = mergeTimeWindows(from);

        List<InstantWindow> out = new ArrayList<>();
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
                var window1 = new SimpleInstantWindow(
                        window.start(),
                        toSubtract.start());
                var window2 = new SimpleInstantWindow(
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
                var truncated = new SimpleInstantWindow(window.start(), toSubtract.start());
                out.add(truncated);
                continue;
            }

            if (!window.start().isBefore(toSubtract.start())
                    && !window.start().isAfter(toSubtract.end())
                    && window.end().isAfter(toSubtract.end())) {
                // Collision on the right side of toSubtract, truncate window
                var truncated = new SimpleInstantWindow(toSubtract.end(), window.end());
                out.add(truncated);
                continue;
            }
        }
        return out;

    }

    // Returned list is sorted in ascending order
    public static List<InstantWindow> mergeTimeWindows(List<InstantWindow> windows) {
        if (windows.size() == 0) {
            return windows;
        }

        List<InstantWindow> sortedWindows = sortByStartAscending(windows);

        List<InstantWindow> mergedWindows = new ArrayList<>();

        InstantWindow currentWindow = sortedWindows.get(0);
        for (int i = 1; i < windows.size(); i++) {
            InstantWindow nextWindow = sortedWindows.get(i);
            boolean nextWindowOverlapsWithCurrent = nextWindow.start().isBefore(currentWindow.end());
            if (nextWindowOverlapsWithCurrent) {
                Instant newEnd = currentWindow.end().isAfter(nextWindow.end())
                        ? currentWindow.end()
                        : nextWindow.end();
                currentWindow = new SimpleInstantWindow(
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

    public static List<InstantWindow> sortByStartAscending(List<InstantWindow> windows) {
        return windows.stream().sorted(Comparator.comparing(InstantWindow::start)).collect(Collectors.toList());
    }

    public static boolean doWindowsCollide(InstantWindow window1, InstantWindow window2) {
        Instant start1 = window1.start();
        Instant end1 = window1.end();
        return window2.start().isBefore(end1) && window2.end().isAfter(start1);
    }

}
