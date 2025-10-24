package pl.michal_sobiech.engineering_thesis.enterprise_service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot.EnterpriseServiceSlotEntity;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot.EnterpriseServiceSlotService;
import pl.michal_sobiech.engineering_thesis.time_slot.TimeSlot;
import pl.michal_sobiech.engineering_thesis.time_slot.TimeSlotWithOccupancy;

@Component
@RequiredArgsConstructor
public class EnterpriseServiceService {

    private final EnterpriseServiceRepository enterpriseServiceRepository;
    private final EnterpriseServiceSlotService enterpriseServiceSlotService;

    @Transactional
    public CreateEnterpriseServiceResult save(long enterpriseId, CreateEnterpriseServiceCommand command) {

        var builder = EnterpriseServiceEntity.builder()
                .enterpriseId(enterpriseId)
                .name(command.name())
                .description(command.description())
                .timeZone(command.timeZone())
                .takesCustomAppointments(command.takesCustomAppointments())
                .price(command.price())
                .currency(command.currency());

        command.location().ifPresent(location -> {
            builder.address(location.getAddress());
            builder.longitude(location.getLongitude());
            builder.latitude(location.getLatitude());
        });

        EnterpriseServiceEntity service = builder.build();
        service = enterpriseServiceRepository.save(service);

        final long enterpriseServiceId = service.getEnterpriseServiceId();
        List<EnterpriseServiceSlotEntity> slots = enterpriseServiceSlotService.saveMany(enterpriseServiceId,
                command.slots());
        return new CreateEnterpriseServiceResult(service, slots);
    }

    public List<EnterpriseServiceEntity> findByEnterpriseId(long enterpriseId) {
        return enterpriseServiceRepository.findByEnterpriseId(enterpriseId);
    }

    public Optional<EnterpriseServiceEntity> findById(long enterpriseServiceId) {
        return enterpriseServiceRepository.findById(enterpriseServiceId);
    }

    public List<? extends TimeSlot> findFreeSlots(long enterpriseServiceId, OffsetDateTime start, OffsetDateTime end) {
        TimeSlotWithOccupancy slot = new TimeSlotWithOccupancy(start, end, 10);
        return List.of(slot);
        // EnterpriseServiceEntity enterpiseService =
        // findById(enterpriseServiceId).orElseThrow();
        // if (enterpiseService.isTakesCustomAppointments()) {
        // return findFreeSlotsOfServiceWithCustomAppointments(enterpiseService, start,
        // end);
        // } else {
        // return findFreeSlotsOfServiceWithoutCustomAppointments(enterpiseService,
        // start, end);
        // }
    }

    // private List<TimeSlotNoOccupancy>
    // findFreeSlotsOfServiceWithCustomAppointments(
    // EnterpriseServiceEntity enterpriseService,
    // OffsetDateTime start,
    // OffsetDateTime end) {
    // ZoneId enterpriseTimeZone = enterpriseService.getTimeZone();
    // OffsetDateTime startInEnterpriseTz =
    // start.atZoneSameInstant(enterpriseTimeZone).toOffsetDateTime();
    // OffsetDateTime endInEnterpriseTz =
    // end.atZoneSameInstant(enterpriseTimeZone).toOffsetDateTime();

    // List<EnterpriseServiceSlotEntity> weekTemplateSlots =
    // enterpriseServiceSlotService
    // .findAllByEnterpriseServiceId(enterpriseService.getEnterpriseServiceId());
    // Map<DayOfWeek, List<EnterpriseServiceSlotEntity>> weekTemplateSlotMap =
    // weekTemplateSlots.stream()
    // .collect(Collectors.groupingBy(EnterpriseServiceSlotEntity::getDayOfWeek));

    // List<TimeSlotNoOccupancy> slots = new ArrayList<>();
    // for (OffsetDateTime date = startInEnterpriseTz;
    // !date.isAfter(endInEnterpriseTz); date = date.plusDays(1)) {
    // DayOfWeek dayOfWeek = date.getDayOfWeek();
    // slots.
    // }

    // List<TimeSlotNoOccupancy> weekTemplateSlotsWithTimeZone =
    // weekTemplateSlots.stream().map(slot -> {

    // // return new TimeSlotNoOccupancy(
    // // slot.g);
    // // });

    // List<TimeSlotNoOccupancy> slots = new ArrayList<>();

    // }

    // private List<TimeSlotWithOccupancy>
    // findFreeSlotsOfServiceWithoutCustomAppointments(
    // EnterpriseServiceEntity enterpriseService,
    // OffsetDateTime start,
    // OffsetDateTime end) {

    // }
}
