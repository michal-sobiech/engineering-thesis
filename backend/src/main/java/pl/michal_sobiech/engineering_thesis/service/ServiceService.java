package pl.michal_sobiech.engineering_thesis.service;

import org.SwaggerCodeGenExample.model.CreateEnterpriseServiceRequest;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ServiceService {

    private final ServiceRepository serviceRepository;

    public Service save(CreateEnterpriseServiceRequest request) {
        Service service = Service.builder()
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
