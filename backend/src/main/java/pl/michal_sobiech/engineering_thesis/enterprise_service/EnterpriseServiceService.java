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

    private final EnterpriseServiceRepository enterpriseServiceRepository;
    private final EnterpriseServiceSlotService enterpriseServiceSlotService;

    @Transactional
    public CreateEnterpriseServiceResult save(long enterpriseId, CreateEnterpriseServiceCommand command) {

        var builder = EnterpriseService.builder()
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

        EnterpriseService service = builder.build();
        service = enterpriseServiceRepository.save(service);

        final long enterpriseServiceId = service.getEnterpriseServiceId();
        List<EnterpriseServiceSlot> slots = enterpriseServiceSlotService.saveMany(enterpriseServiceId, command.slots());
        return new CreateEnterpriseServiceResult(service, slots);
    }

    public List<EnterpriseService> findByEnterpriseId(long enterpriseId) {
        return enterpriseServiceRepository.findByEnterpriseId(enterpriseId);
    }
}
