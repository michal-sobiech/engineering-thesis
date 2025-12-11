package pl.michal_sobiech.core.enterprise_service_availability;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.appointment.custom.CustomAppointmentService;
import pl.michal_sobiech.core.enterprise_service.EnterpriseServiceService;
import pl.michal_sobiech.core.enterprise_service_slot_template.custom_appointments.CustomTimeWindowTemplateService;
import pl.michal_sobiech.core.utils.DateUtils;
import pl.michal_sobiech.core.utils.LocalDateTimeWindow;

@RequiredArgsConstructor
public class CustomEnterpriseServiceAvailabilityService {

    private final EnterpriseServiceService enterpriseServiceService;
    private final CustomTimeWindowTemplateService customAppointmentsEnterpriseServiceTimeWindowTemplateService;
    private final CustomAppointmentService customAppointmentService;

    // public boolean isEnterpriseServiceAvailableInServiceLocalDatetimeRange(
    // long enterpriseServiceId,
    // LocalDateTime fromInServiceTimezone,
    // LocalDateTime toInServiceTimezone) {
    // List<
    // }

    public List<LocalDateTimeWindow> findFreeTimeWindowsInLocalDatetimeRangeForService(
            long enterpriseServiceId,
            LocalDateTime from,
            LocalDateTime to) {
        // 1. Get availability template
        // 2. Get appointments in datetime range
        // 3. Subtract

        ZoneId timezone = enterpriseServiceService.getTimeZoneByServiceId(enterpriseServiceId);

        // 1.
        List<LocalDateTimeWindow> defaultAvailability = customAppointmentsEnterpriseServiceTimeWindowTemplateService
                .getAvailabilityTemplateForLocalDatetimeRange(
                        enterpriseServiceId,
                        from,
                        to);

        // 2.
        List<LocalDateTimeWindow> confirmedAppointmentWindows = customAppointmentService
                .getConfirmedAppointmentsInDatetimeRange(
                        enterpriseServiceId,
                        from.atZone(timezone).toInstant(),
                        to.atZone(timezone).toInstant())
                .stream()
                .map(a -> new LocalDateTimeWindow(
                        DateUtils.createLocalDateTime(a.startInstant(), timezone),
                        DateUtils.createLocalDateTime(a.endInstant(), timezone)))
                .collect(Collectors.toList());

        // 3.
        return DateUtils.subtractTimeWindowLists(defaultAvailability, confirmedAppointmentWindows);
    }

}
