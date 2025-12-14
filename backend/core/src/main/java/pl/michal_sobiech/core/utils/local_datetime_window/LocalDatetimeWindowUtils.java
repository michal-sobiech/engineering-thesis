package pl.michal_sobiech.core.utils.local_datetime_window;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class LocalDatetimeWindowUtils {

    public static boolean doesPeriodCollideWithLocalDatetimeWindow(LocalDateTime periodStart, LocalDateTime periodEnd,
            LocalDatetimeWindow window) {
        LocalDatetimeWindow periodWindow = new SimpleLocalDatetimeWindow(periodStart, periodEnd);
        return doWindowsCollide(periodWindow, window);
    }

    public static <T extends LocalDatetimeWindow> List<T> filterWindowsFullyContainedInRange(
            List<T> windows,
            LocalDateTime from, LocalDateTime to) {
        return windows.stream()
                .filter(window -> doesPeriodCollideWithLocalDatetimeWindow(from, to, window))
                .collect(Collectors.toList());
    }

    public static boolean doWindowsCollide(LocalDatetimeWindow window1, LocalDatetimeWindow window2) {
        LocalDateTime start1 = window1.start();
        LocalDateTime end1 = window1.end();
        return window2.start().isBefore(end1) && window2.end().isAfter(start1);
    }

}
