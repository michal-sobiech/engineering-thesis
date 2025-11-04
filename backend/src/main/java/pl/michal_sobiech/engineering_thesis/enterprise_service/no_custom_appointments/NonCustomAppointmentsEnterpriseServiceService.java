package pl.michal_sobiech.engineering_thesis.enterprise_service.no_custom_appointments;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.appointment.non_custom.NonCustomAppointment;
import pl.michal_sobiech.engineering_thesis.appointment.non_custom.NonCustomAppointmentsService;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceEntity;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceRepository;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceService;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.EnterpriseServiceSlotTemplateService;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.non_custom_appointments.CreateNonCustomAppointmentsEnterpriseServiceSlotTemplateCommand;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot_template.non_custom_appointments.NonCustomAppointmentsEnterpriseServiceSlotTemplateService;
import pl.michal_sobiech.engineering_thesis.utils.DateUtils;
import pl.michal_sobiech.engineering_thesis.utils.LocalDateTimeWindow;

@Service
@RequiredArgsConstructor
public class NonCustomAppointmentsEnterpriseServiceService {

    private final EnterpriseServiceRepository enterpriseServiceRepository;
    private final EnterpriseServiceSlotTemplateService enterpriseServiceSlotTemplateService;
    private final EnterpriseServiceService enterpriseServiceService;
    private final NonCustomAppointmentsEnterpriseServiceSlotTemplateService nonCustomAppointmentsEnterpriseServiceSlotTemplateService;
    private final NonCustomAppointmentsService nonCustomAppointmentsService;

    @Transactional
    public EnterpriseServiceEntity save(long enterpriseId, CreateNoCustomAppointmentsEnterpriseServiceCommand command) {
        EnterpriseServiceEntity service = EnterpriseServiceEntity.builder()
                .enterpriseId(enterpriseId)
                .name(command.name())
                .description(command.description())
                .timeZone(command.timeZone())
                .takesCustomAppointments(false)
                .maxDistanceKm(null)
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
    public void saveWithSlotTemplates(long enterpriseId,
            CreateNoCustomAppointmentsEnterpriseServiceCommand command,
            List<CreateNonCustomAppointmentsEnterpriseServiceSlotTemplateCommand> slots) {
        EnterpriseServiceEntity service = save(enterpriseId, command);

        final long enterpriseServiceId = service.getEnterpriseServiceId();
        nonCustomAppointmentsEnterpriseServiceSlotTemplateService.saveMany(enterpriseServiceId, slots);
    }

    public List<LocalDateTimeWindow> findFreeTimeWindowsInDatetimeRange(
            long serviceId,
            OffsetDateTime from,
            OffsetDateTime to) {
        ZoneId timeZone = enterpriseServiceService.getTimeZoneByServiceId(serviceId);

        LocalDateTime fromInServiceTimezone = DateUtils.createLocalDateTime(from, timeZone);
        LocalDateTime toInServiceTimezone = DateUtils.createLocalDateTime(to, timeZone);

        List<LocalDateTimeWindow> defaultAvailability = nonCustomAppointmentsEnterpriseServiceSlotTemplateService
                .getAvailabilityTemplateForDatetimeRange(serviceId, fromInServiceTimezone,
                        toInServiceTimezone);

        List<NonCustomAppointment> appointments = nonCustomAppointmentsService.getAllByServiceIdAndRange(
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
    }

}
