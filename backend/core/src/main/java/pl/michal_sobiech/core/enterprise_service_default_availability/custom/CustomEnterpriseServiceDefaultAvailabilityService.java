package pl.michal_sobiech.core.enterprise_service_default_availability.custom;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.enterprise_service_slot_template.EnterpriseServiceSlotTemplateService;
import pl.michal_sobiech.core.enterprise_service_slot_template.custom_appointments.CustomSlotTemplate;
import pl.michal_sobiech.core.mapper.TimeWindowMapper;
import pl.michal_sobiech.core.utils.DateUtils;
import pl.michal_sobiech.core.utils.local_datetime_window.LocalDatetimeWindow;
import pl.michal_sobiech.core.utils.local_datetime_window.LocalDatetimeWindowUtils;

@RequiredArgsConstructor
public class CustomEnterpriseServiceDefaultAvailabilityService {

    private final EnterpriseServiceSlotTemplateService enterpriseServiceSlotTemplateService;

    public List<LocalDatetimeWindow> getDefaultAvailabilityForDatetimeRange(
            long enterpriseServiceId,
            LocalDateTime from,
            LocalDateTime to) {
        List<LocalDatetimeWindow> fullDaysTemplate = getDefaultAvailabilityForDateRange(
                enterpriseServiceId,
                from.toLocalDate(),
                to.toLocalDate());
        return LocalDatetimeWindowUtils.filterWindowsFullyContainedInRange(fullDaysTemplate, from, to);
    }

    public List<LocalDatetimeWindow> getDefaultAvailabilityForDateRange(
            long enterpriseServiceId,
            LocalDate from,
            LocalDate to) {
        List<LocalDatetimeWindow> out = new ArrayList<>();

        for (LocalDate date : DateUtils.getAllDatesBetweenIncludingBorders(from, to)) {
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            System.out.println(dayOfWeek);

            List<LocalDatetimeWindow> windowsForDate = getDefaultAvailabilityForDate(enterpriseServiceId, date);

            out.addAll(windowsForDate);
        }
        return out;
    }

    public List<LocalDatetimeWindow> getDefaultAvailabilityForDate(
            long enterpriseServiceId,
            LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return getDefaultAvailabilityForDayOfWeek(enterpriseServiceId, dayOfWeek)
                .stream()
                .map(window -> TimeWindowMapper.toSimpleLocalDatetimeWindow(window, date))
                .collect(Collectors.toList());
    }

    private List<CustomSlotTemplate> getDefaultAvailabilityForDayOfWeek(
            long enterpiseServiceId,
            DayOfWeek dayOfWeek) {
        return enterpriseServiceSlotTemplateService.getAvailabilityTemplateForDayOfWeek(enterpiseServiceId, dayOfWeek)
                .stream()
                .map(CustomSlotTemplate::from)
                .collect(Collectors.toList());
    }

}
