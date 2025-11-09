package pl.michal_sobiech.engineering_thesis.enterprise_service_availability.custom;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.appointment.custom.ConfirmedCustomAppointment;
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

    public List<LocalDateTimeWindow> findFreeTimeWindowsInDatetimeRange(
            long serviceId,
            OffsetDateTime from,
            OffsetDateTime to) {
        ZoneId timeZone = enterpriseServiceService.getTimeZoneByServiceId(serviceId);

        LocalDateTime fromInServiceTimezone = DateUtils.createLocalDateTime(from, timeZone);
        LocalDateTime toInServiceTimezone = DateUtils.createLocalDateTime(to, timeZone);

        List<LocalDateTimeWindow> defaultAvailability = customAppointmentsEnterpriseServiceTimeWindowTemplateService
                .getAvailabilityTemplateForDatetimeRange(serviceId, fromInServiceTimezone,
                        toInServiceTimezone);

        List<ConfirmedCustomAppointment> confirmedAppointments = customAppointmentsService
                .getConfirmedAppointmentsInDatetimeRange(serviceId, from, to);
        List<LocalDateTimeWindow> confirmedAppointmentsWindows = confirmedAppointments.stream().map(a -> {
            return new LocalDateTimeWindow(
                    DateUtils.createLocalDateTime(a.startInstant(), timeZone),
                    DateUtils.createLocalDateTime(a.endInstant(), timeZone));
        }).collect(Collectors.toList());

        return DateUtils.subtractTimeWindowLists(defaultAvailability, confirmedAppointmentsWindows);
    }

}
