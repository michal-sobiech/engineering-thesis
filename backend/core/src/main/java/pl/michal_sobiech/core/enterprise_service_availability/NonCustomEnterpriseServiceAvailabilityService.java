package pl.michal_sobiech.engineering_thesis.enterprise_service_availability;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceService;
import pl.michal_sobiech.engineering_thesis.enterprise_service_availability_template.EnterpriseServiceAvailabilityTemplateService;
import pl.michal_sobiech.shared.appointment.non_custom.NonCustomAppointment;
import pl.michal_sobiech.shared.appointment.non_custom.NonCustomAppointmentsService;
import pl.michal_sobiech.shared.utils.DateUtils;
import pl.michal_sobiech.shared.utils.LocalDateTimeWindow;


@RequiredArgsConstructor
public class NonCustomEnterpriseServiceAvailabilityService {

        private final NonCustomAppointmentsService nonCustomAppointmentsService;
        private final EnterpriseServiceService enterpriseServiceService;
        private final EnterpriseServiceAvailabilityTemplateService enterpriseServiceAvailabilityTemplateService;

        public List<LocalDateTimeWindow> getAvailableSlotsInDatetimeRangeForService(
                        long enterpriseServiceId,
                        LocalDateTime from,
                        LocalDateTime to) {
                // 1. Get availability template
                // 2. Get appointments in datetime range
                // 3. Subtract

                // TODO add capacity
                // 1.
                List<LocalDateTimeWindow> availabilityTemplate = enterpriseServiceAvailabilityTemplateService
                                .getAvailabilityTemplateForDatetimeRange(enterpriseServiceId, from, to);

                // 2.
                ZoneId timezone = enterpriseServiceService.getTimeZoneByServiceId(enterpriseServiceId);

                OffsetDateTime fromOffset = DateUtils.createOffsetDateTime(from, timezone);
                OffsetDateTime toOffset = DateUtils.createOffsetDateTime(to, timezone);

                List<NonCustomAppointment> appointments = nonCustomAppointmentsService
                                .getAllByServiceIdAndDatetimeRange(enterpriseServiceId, fromOffset, toOffset);

                List<LocalDateTimeWindow> appointmentWindows = appointments.stream()
                                .map(a -> new LocalDateTimeWindow(
                                                DateUtils.createLocalDateTime(a.startInstant(), timezone),
                                                DateUtils.createLocalDateTime(a.endInstant(), timezone)))
                                .collect(Collectors.toList());

                // 3.
                return DateUtils.subtractTimeWindowLists(availabilityTemplate, appointmentWindows);
        }

        public List<LocalDateTimeWindow> findFreeTimeWindowsInDatetimeRange(
                        long serviceId,
                        OffsetDateTime from,
                        OffsetDateTime to) {
                ZoneId timeZone = enterpriseServiceService.getTimeZoneByServiceId(serviceId);

                LocalDateTime fromInServiceTimezone = DateUtils.createLocalDateTime(from, timeZone);
                LocalDateTime toInServiceTimezone = DateUtils.createLocalDateTime(to, timeZone);

                List<LocalDateTimeWindow> defaultAvailability = enterpriseServiceAvailabilityTemplateService
                                .getAvailabilityTemplateForDatetimeRange(serviceId, fromInServiceTimezone,
                                                toInServiceTimezone);

                List<NonCustomAppointment> appointments = nonCustomAppointmentsService
                                .getAllByServiceIdAndDatetimeRange(
                                                serviceId,
                                                from, to);
                // List<LocalDateTimeWindow> appointmentWindows = appointments.stream().map(a ->
                // {
                // return new LocalDateTimeWindow(
                // DateUtils.createLocalDateTime(a.startTime(), timeZone),
                // DateUtils.createLocalDateTime(a.endTime(), timeZone));
                // }).collect(Collectors.toList());
                List<LocalDateTimeWindow> appointmentWindows = new ArrayList<>(List.of(
                                defaultAvailability.get(1)));

                return DateUtils.subtractTimeWindowLists(defaultAvailability,
                                appointmentWindows);

                // return defaultAvailability;

                // TODO
        }

}
