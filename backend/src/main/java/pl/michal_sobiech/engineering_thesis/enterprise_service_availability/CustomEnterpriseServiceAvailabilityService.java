package pl.michal_sobiech.engineering_thesis.enterprise_service_availability;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.appointment.custom.CustomAppointmentsService;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceService;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.custom_appointments.CustomAppointmentsEnterpriseServiceTimeWindowTemplateService;
import pl.michal_sobiech.engineering_thesis.utils.DateUtils;
import pl.michal_sobiech.engineering_thesis.utils.LocalDateTimeWindow;

@Service
@RequiredArgsConstructor
public class CustomEnterpriseServiceAvailabilityService {

    private final EnterpriseServiceService enterpriseServiceService;
    private final CustomAppointmentsEnterpriseServiceTimeWindowTemplateService customAppointmentsEnterpriseServiceTimeWindowTemplateService;
    private final CustomAppointmentsService customAppointmentsService;

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
        List<LocalDateTimeWindow> confirmedAppointmentWindows = customAppointmentsService
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
