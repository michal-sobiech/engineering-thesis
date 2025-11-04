package pl.michal_sobiech.engineering_thesis.enterprise_service.custom_appointments;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.appointment.custom.ConfirmedCustomAppointment;
import pl.michal_sobiech.engineering_thesis.appointment.custom.CustomAppointmentsService;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceEntity;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceRepository;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceService;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.custom_appointments.CreateCustomAppointmentsEnterpriseServiceTimeWindowTemplateCommand;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.custom_appointments.CustomAppointmentsEnterpriseServiceTimeWindowTemplateService;
import pl.michal_sobiech.engineering_thesis.utils.DateUtils;
import pl.michal_sobiech.engineering_thesis.utils.LocalDateTimeWindow;

@Service
@RequiredArgsConstructor
public class CustomAppointmentsEnterpriseServiceService {

        private final EnterpriseServiceRepository enterpriseServiceRepository;
        private final CustomAppointmentsEnterpriseServiceTimeWindowTemplateService customAppointmentsEnterpriseServiceTimeWindowTemplateService;
        private final CustomAppointmentsService customAppointmentsService;
        private final EnterpriseServiceService enterpriseServiceService;

        @Transactional
        public EnterpriseServiceEntity save(long enterpriseId,
                        CreateCustomAppointmentsEnterpriseServiceCommand command) {
                EnterpriseServiceEntity service = EnterpriseServiceEntity.builder()
                                .enterpriseId(enterpriseId)
                                .name(command.name())
                                .description(command.description())
                                .timeZone(command.timeZone())
                                .takesCustomAppointments(true)
                                .maxDistanceKm(command.maxDistanceKm())
                                .cathegory(command.cathegory())
                                .price(command.price().orElse(null))
                                .currency(command.currency())
                                .address(command.location().getAddress())
                                .longitude(command.location().getLongitude())
                                .latitude(command.location().getLatitude())
                                .build();
                return enterpriseServiceRepository.save(service);
        }

        @Transactional
        public void saveWithTImeWindows(
                        long enterpriseId,
                        CreateCustomAppointmentsEnterpriseServiceCommand command,
                        List<CreateCustomAppointmentsEnterpriseServiceTimeWindowTemplateCommand> timeWindows) {
                EnterpriseServiceEntity enterpriseService = save(enterpriseId, command);

                final long enterpriseServiceId = enterpriseService.getEnterpriseServiceId();
                customAppointmentsEnterpriseServiceTimeWindowTemplateService.saveMany(
                                enterpriseServiceId,
                                timeWindows);
        }

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
                                        DateUtils.createLocalDateTime(a.startTime(), timeZone),
                                        DateUtils.createLocalDateTime(a.endTime(), timeZone));
                }).collect(Collectors.toList());

                return DateUtils.subtractTimeWindowLists(defaultAvailability, confirmedAppointmentsWindows);
        }

}
