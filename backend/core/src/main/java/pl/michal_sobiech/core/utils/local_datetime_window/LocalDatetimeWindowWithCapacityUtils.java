package pl.michal_sobiech.core.utils.local_datetime_window;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LocalDatetimeWindowWithCapacityUtils {

    public static List<LocalDatetimeWindowWithCapacity> subtractTimeWindowsWithoutCapacity(
            List<LocalDatetimeWindowWithCapacity> from,
            List<LocalDatetimeWindow> what) {
        // Create a map with window capacities and fill it
        Map<LocalDatetimeWindow, Integer> windowsAndVacancies = new HashMap<>();
        from.forEach(window -> {
            var key = new SimpleLocalDatetimeWindow(window.start(), window.end());
            var value = window.capacity();
            windowsAndVacancies.put(key, value);
        });

        for (LocalDatetimeWindow whatWindow : what) {
            for (LocalDatetimeWindow fromWindow : windowsAndVacancies.keySet()) {
                if (LocalDatetimeWindowUtils.doWindowsCollide(whatWindow, fromWindow)) {
                    int vacancies = windowsAndVacancies.get(fromWindow);
                    vacancies -= 1;
                    windowsAndVacancies.put(fromWindow, vacancies);
                }
            }
        }

        // Leave only windows with positive
        Map<LocalDatetimeWindow, Integer> filteredWindowsAndVacancies = windowsAndVacancies.entrySet()
                .stream()
                .filter(entry -> {
                    int capacity = entry.getValue();
                    return capacity > 0;
                })
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue));

        return filteredWindowsAndVacancies.entrySet()
                .stream()
                .map(entry -> new LocalDatetimeWindowWithCapacity(
                        entry.getKey().start(),
                        entry.getKey().end(),
                        entry.getValue()))
                .collect(Collectors.toList());
    }

}
