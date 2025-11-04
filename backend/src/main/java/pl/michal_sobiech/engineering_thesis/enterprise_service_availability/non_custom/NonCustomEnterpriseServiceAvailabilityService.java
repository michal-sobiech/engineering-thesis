package pl.michal_sobiech.engineering_thesis.enterprise_service_availability.non_custom;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.appointment.non_custom.NonCustomAppointment;
import pl.michal_sobiech.engineering_thesis.appointment.non_custom.NonCustomAppointmentsService;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceService;
import pl.michal_sobiech.engineering_thesis.enterprise_service_availability_template.non_custom.NonCustomEnterpriseServiceAvailabilityTemplateService;
import pl.michal_sobiech.engineering_thesis.utils.DateUtils;
import pl.michal_sobiech.engineering_thesis.utils.LocalDateTimeWindow;

@Service
@RequiredArgsConstructor
public class NonCustomEnterpriseServiceAvailabilityService {

        private final NonCustomAppointmentsService nonCustomAppointmentsService;
        private final EnterpriseServiceService enterpriseServiceService;
        private final NonCustomEnterpriseServiceAvailabilityTemplateService nonCustomEnterpriseServiceAvailabilityTemplateService;

        public List<LocalDateTimeWindow> getAvailableSlotsInDatetimeRange(long entepriseServiceId, LocalDateTime from,
                        LocalDateTime to) {
                // 1. Get availability template
                // 2. Get appointments in datetime range
                // 3. Subtract

                // 1.
                List<LocalDateTimeWindow> availabilityTemplate = nonCustomEnterpriseServiceAvailabilityTemplateService
                                .getAvailabilityTemplateForDatetimeRange(entepriseServiceId, from, to);

                // 2.
                ZoneId timezone = enterpriseServiceService.getTimeZoneByServiceId(entepriseServiceId);

                OffsetDateTime fromOffset = DateUtils.createOffsetDateTime(from, timezone);
                OffsetDateTime toOffset = DateUtils.createOffsetDateTime(to, timezone);

                List<NonCustomAppointment> appointments = nonCustomAppointmentsService
                                .getAllByServiceIdAndDatetimeRange(entepriseServiceId, fromOffset, toOffset);

                List<LocalDateTimeWindow> appointmentWindows = appointments.stream()
                                .map(a -> new LocalDateTimeWindow(
                                                DateUtils.createLocalDateTime(a.startDatetime(), timezone),
                                                DateUtils.createLocalDateTime(a.endDatetime(), timezone)))
                                .collect(Collectors.toList());

                // 3.
                return DateUtils.subtractTimeWindowLists(availabilityTemplate, appointmentWindows);
        }

}
