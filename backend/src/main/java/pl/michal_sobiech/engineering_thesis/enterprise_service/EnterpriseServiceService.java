package pl.michal_sobiech.engineering_thesis.enterprise_service;

import org.SwaggerCodeGenExample.model.CreateEnterpriseServiceRequest;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EnterpriseServiceService {

    private final EntepriseServiceRepository serviceRepository;

    public EnterpriseService save(CreateEnterpriseServiceRequest request) {
        EnterpriseService service = EnterpriseService.builder()
                .name(request.getName())
                .description(request.getDescription())
                .location(request.getLocation().orElse(null))
                .takesCustomAppointments(request.getTakesCustomAppointments())
                .price(request.getPrice())
                .currency(request.getCurrency())
                .build();
        return serviceRepository.save(service);
    }
}
