package pl.michal_sobiech.core.enterprise_service_availability_template;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.EnterpriseServiceSlotTemplateService;
import pl.michal_sobiech.engineering_thesis.utils.LocalTimeWindow;
import pl.michal_sobiech.shared.utils.DateUtils;
import pl.michal_sobiech.shared.utils.LocalDateTimeWindow;

@RequiredArgsConstructor
public class EnterpriseServiceAvailabilityTemplateService {

    private final EnterpriseServiceSlotTemplateService enterpriseServiceSlotTemplateService;

    public List<LocalDateTimeWindow> getAvailabilityTemplateForDatetimeRange(
            long enterpriseServiceId,
            LocalDateTime from,
            LocalDateTime to) {
        List<LocalDateTimeWindow> fullDaysTemplate = getAvailabilityTemplateForDateRange(
                enterpriseServiceId,
                from.toLocalDate(),
                to.toLocalDate());
        return DateUtils.filterWindowsFullyContainedInRange(fullDaysTemplate, from, to);
    }

    public List<LocalDateTimeWindow> getAvailabilityTemplateForDate(
            long enterpriseServiceId,
            LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return getAvailabilityTemplateForDayOfWeek(enterpriseServiceId, dayOfWeek)
                .stream()
                .map(window -> new LocalDateTimeWindow(
                        date.atTime(window.start()),
                        date.atTime(window.end())))
                .collect(Collectors.toList());
    }

    public List<LocalDateTimeWindow> getAvailabilityTemplateForDateRange(
            long enterpriseServiceId,
            LocalDate from,
            LocalDate to) {
        List<LocalDateTimeWindow> out = new ArrayList<>();

        for (LocalDate date : DateUtils.getAllDatesBetweenIncludingBorders(from, to)) {
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            System.out.println(dayOfWeek);

            List<LocalDateTimeWindow> windowsForDate = getAvailabilityTemplateForDate(enterpriseServiceId,
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
