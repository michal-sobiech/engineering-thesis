package pl.michal_sobiech.engineering_thesis.enterprise_service;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EnterpriseServiceService {

    private final EntepriseServiceRepository serviceRepository;

    public EnterpriseService save(CreateEnterpriseServiceCommand command) {
        EnterpriseService service = EnterpriseService.builder()
                .name(command.name())
                .description(command.description())
                .location(command.location().orElse(null))
                .timeZone(command.timeZone())
                .takesCustomAppointments(command.takesCustomAppointments())
                .price(command.price())
                .currency(command.currency())
                .build();
        return serviceRepository.save(service);
    }
}
