package pl.michal_sobiech.core.utils.instant_window;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InstantWindowWithCapacityUtils {

    public static List<InstantWindowWithCapacity> subtractTimeWindowsWithoutCapacity(
            List<InstantWindowWithCapacity> from,
            List<InstantWindow> what) {

        // Create a map with window capacities and fill it
        Map<InstantWindow, Integer> windowsAndVacancies = new HashMap<>();
        from.forEach(window -> {
            var key = new SimpleInstantWindow(window.start(), window.end());
            var value = window.capacity();
            windowsAndVacancies.put(key, value);
        });

        for (InstantWindow whatWindow : what) {
            for (InstantWindow fromWindow : windowsAndVacancies.keySet()) {
                if (InstantWindowUtils.doWindowsCollide(whatWindow, fromWindow)) {
                    int vacancies = windowsAndVacancies.get(fromWindow);
                    vacancies -= 1;
                    windowsAndVacancies.put(fromWindow, vacancies);
                }
            }
        }

        // Leave only windows with positive
        Map<InstantWindow, Integer> filteredWindowsAndVacancies = windowsAndVacancies.entrySet()
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
                .map(entry -> new InstantWindowWithCapacity(
                        entry.getKey().start(),
                        entry.getKey().end(),
                        entry.getValue()))
                .collect(Collectors.toList());
    }

}
