package pl.michal_sobiech.core.enterprise_service_default_availability.non_custom;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.enterprise_service.EnterpriseServiceService;
import pl.michal_sobiech.core.enterprise_service_slot_template.EnterpriseServiceSlotTemplateService;
import pl.michal_sobiech.core.enterprise_service_slot_template.non_custom_appointments.NonCustomSlotTemplate;
import pl.michal_sobiech.core.mapper.TimeWindowMapper;
import pl.michal_sobiech.core.utils.DateUtils;
import pl.michal_sobiech.core.utils.instant_window.InstantWindowWithCapacity;
import pl.michal_sobiech.core.utils.local_datetime_window.LocalDatetimeWindowUtils;
import pl.michal_sobiech.core.utils.local_datetime_window.LocalDatetimeWindowWithCapacity;

@RequiredArgsConstructor
public class NonCustomEnterpriseServiceDefaultAvailabilityService {

    private final EnterpriseServiceSlotTemplateService enterpriseServiceSlotTemplateService;
    private final EnterpriseServiceService enterpriseServiceService;

    public List<InstantWindowWithCapacity> getDefaultServiceAvailabilityInInstantRange(
            long enterpiseServiceId,
            Instant from,
            Instant to) {
        ZoneId serviceTimezone = enterpriseServiceService.getTimeZoneByServiceId(enterpiseServiceId);

        LocalDateTime fromLocal = LocalDateTime.ofInstant(from, serviceTimezone);
        LocalDateTime toLocal = LocalDateTime.ofInstant(to, serviceTimezone);

        return getDefaultAvailabilityForDatetimeRangeServiceTz(enterpiseServiceId, fromLocal, toLocal)
                .stream()
                .map(localWindow -> {
                    Instant startInstant = localWindow.start().atZone(serviceTimezone).toInstant();
                    Instant endInstant = localWindow.end().atZone(serviceTimezone).toInstant();
                    return new InstantWindowWithCapacity(startInstant, endInstant, localWindow.capacity());
                })
                .collect(Collectors.toList());
    }

    private List<LocalDatetimeWindowWithCapacity> getDefaultAvailabilityForDatetimeRangeServiceTz(
            long enterpriseServiceId,
            LocalDateTime from,
            LocalDateTime to) {
        List<LocalDatetimeWindowWithCapacity> dateRangeDefaultAvailability = getDefaultAvailabilityForDateRangeServiceTz(
                enterpriseServiceId,
                from.toLocalDate(),
                to.toLocalDate());
        return LocalDatetimeWindowUtils.filterWindowsFullyContainedInRange(dateRangeDefaultAvailability, from, to);
    }

    private List<LocalDatetimeWindowWithCapacity> getDefaultAvailabilityForDateRangeServiceTz(
            long enterpriseServiceId,
            LocalDate from,
            LocalDate to) {
        List<LocalDatetimeWindowWithCapacity> out = new ArrayList<>();

        for (LocalDate date : DateUtils.getAllDatesBetweenIncludingBorders(from, to)) {
            List<LocalDatetimeWindowWithCapacity> windowsForDate = getDefaultAvailabilityForDateServiceTz(
                    enterpriseServiceId, date);
            out.addAll(windowsForDate);
        }
        return out;
    }

    private List<LocalDatetimeWindowWithCapacity> getDefaultAvailabilityForDateServiceTz(
            long enterpriseServiceId,
            LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        List<NonCustomSlotTemplate> windows = getDefaultAvailabilityForDayOfWeek(enterpriseServiceId, dayOfWeek);
        return windows.stream()
                .map(window -> TimeWindowMapper.toLocalDatetimeWindowWithCapacity(window, date))
                .collect(Collectors.toList());
    }

    private List<NonCustomSlotTemplate> getDefaultAvailabilityForDayOfWeek(
            long enterpiseServiceId,
            DayOfWeek dayOfWeek) {
        return enterpriseServiceSlotTemplateService.getAvailabilityTemplateForDayOfWeek(enterpiseServiceId, dayOfWeek)
                .stream()
                .map(NonCustomSlotTemplate::from)
                .collect(Collectors.toList());
    }

}
