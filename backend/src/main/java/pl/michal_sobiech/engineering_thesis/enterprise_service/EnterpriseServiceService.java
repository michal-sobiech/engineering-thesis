package pl.michal_sobiech.engineering_thesis.enterprise_service;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot.EnterpriseServiceSlot;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot.EnterpriseServiceSlotService;

@Component
@RequiredArgsConstructor
public class EnterpriseServiceService {

    private final EnterpriseServiceRepository serviceRepository;

    private final EnterpriseServiceSlotService enterpriseServiceSlotService;

    @Transactional
    public CreateEnterpriseServiceResult save(CreateEnterpriseServiceCommand command) {
        EnterpriseService service = EnterpriseService.builder()
                .name(command.name())
                .description(command.description())
                .location(command.location().orElse(null))
                .timeZone(command.timeZone())
                .takesCustomAppointments(command.takesCustomAppointments())
                .price(command.price())
                .currency(command.currency())
                .build();
        service = serviceRepository.save(service);

        final long enterpriseServiceId = service.getEnterpriseServiceId();
        List<EnterpriseServiceSlot> slots = enterpriseServiceSlotService.saveMany(enterpriseServiceId, command.slots());
        return new CreateEnterpriseServiceResult(service, slots);
    }
}
