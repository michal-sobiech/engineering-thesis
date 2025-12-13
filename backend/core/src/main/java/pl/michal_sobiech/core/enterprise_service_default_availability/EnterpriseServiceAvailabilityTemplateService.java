package pl.michal_sobiech.core.enterprise_service_default_availability;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.enterprise_service_slot_template.EnterpriseServiceSlotTemplateService;
import pl.michal_sobiech.core.utils.DateUtils;
import pl.michal_sobiech.core.utils.local_datetime_window.LocalDatetimeWindow;
import pl.michal_sobiech.core.utils.local_datetime_window.LocalDatetimeWindowUtils;
import pl.michal_sobiech.core.utils.local_time_window.LocalTimeWindow;

@RequiredArgsConstructor
public class EnterpriseServiceAvailabilityTemplateService {

    private final EnterpriseServiceSlotTemplateService enterpriseServiceSlotTemplateService;

    public List<LocalDatetimeWindow> getAvailabilityTemplateForDatetimeRange(
            long enterpriseServiceId,
            LocalDateTime from,
            LocalDateTime to) {
        List<LocalDatetimeWindow> fullDaysTemplate = getAvailabilityTemplateForDateRange(
                enterpriseServiceId,
                from.toLocalDate(),
                to.toLocalDate());
        return LocalDatetimeWindowUtils.filterWindowsFullyContainedInRange(fullDaysTemplate, from, to);
    }

    public List<LocalDatetimeWindow> getAvailabilityTemplateForDate(
            long enterpriseServiceId,
            LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return getAvailabilityTemplateForDayOfWeek(enterpriseServiceId, dayOfWeek)
                .stream()
                .map(window -> new LocalDatetimeWindow(
                        date.atTime(window.start()),
                        date.atTime(window.end())))
                .collect(Collectors.toList());
    }

    public List<LocalDatetimeWindow> getAvailabilityTemplateForDateRange(
            long enterpriseServiceId,
            LocalDate from,
            LocalDate to) {
        List<LocalDatetimeWindow> out = new ArrayList<>();

        for (LocalDate date : DateUtils.getAllDatesBetweenIncludingBorders(from, to)) {
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            System.out.println(dayOfWeek);

            List<LocalDatetimeWindow> windowsForDate = getAvailabilityTemplateForDate(enterpriseServiceId,
                    date);

            out.addAll(windowsForDate);
        }
        return out;
    }

    public List<LocalTimeWindow> getAvailabilityTemplateForDayOfWeek(
            long enterpiseServiceId,
            DayOfWeek dayOfWeek) {
        return enterpriseServiceSlotTemplateService
                .getAvailabilityTemplateForDayOfWeek(enterpiseServiceId, dayOfWeek)
                .stream()
                .map(slotTemplate -> new LocalTimeWindow(slotTemplate.getStartTime(),
                        slotTemplate.getEndTime()))
                .collect(Collectors.toList());
    }
}
