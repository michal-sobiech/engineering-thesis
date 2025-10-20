package pl.michal_sobiech.engineering_thesis.enterprise_service;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot.EnterpriseServiceSlot;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot.EnterpriseServiceSlotService;
import pl.michal_sobiech.engineering_thesis.location.Location;
import pl.michal_sobiech.engineering_thesis.location.LocationService;

@Component
@RequiredArgsConstructor
public class EnterpriseServiceService {

    private final EnterpriseServiceRepository enterpriseServiceRepository;
    private final EnterpriseServiceSlotService enterpriseServiceSlotService;
    private final LocationService locationService;

    @Transactional
    public CreateEnterpriseServiceResult save(long enterpriseId, CreateEnterpriseServiceCommand command) {

        var serviceBuilder = EnterpriseService.builder()
                .enterpriseId(enterpriseId)
                .name(command.name())
                .description(command.description())
                .timeZone(command.timeZone())
                .takesCustomAppointments(command.takesCustomAppointments())
                .price(command.price())
                .currency(command.currency());

        if (command.location().isPresent()) {
            var location = command.location().get();
            Location locationDb = locationService.save(location);
            serviceBuilder = serviceBuilder.locationId(locationDb.getLocationId());
        }

        EnterpriseService service = serviceBuilder.build();
        service = enterpriseServiceRepository.save(service);

        final long enterpriseServiceId = service.getEnterpriseServiceId();
        List<EnterpriseServiceSlot> slots = enterpriseServiceSlotService.saveMany(enterpriseServiceId, command.slots());
        return new CreateEnterpriseServiceResult(service, slots);
    }

    public List<EnterpriseService> findByEnterpriseId(long enterpriseId) {
        return enterpriseServiceRepository.findByEnterpriseId(enterpriseId);
    }
}
