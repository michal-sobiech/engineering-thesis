package pl.michal_sobiech.engineering_thesis.enterprise_service;

import java.util.List;
import java.util.stream.Collectors;

import org.SwaggerCodeGenExample.model.GetEnterpriseServiceNonCustomServiceResponse;
import org.SwaggerCodeGenExample.model.Slot;

import pl.michal_sobiech.core.enterprise.Enterprise;
import pl.michal_sobiech.core.enterprise_service.no_custom_appointments.NonCustomEnterpriseService;
import pl.michal_sobiech.core.enterprise_service_slot_template.non_custom_appointments.NonCustomSlotTemplate;
import pl.michal_sobiech.engineering_thesis.api.LocationMapper;
import pl.michal_sobiech.engineering_thesis.api.SlotMapper;

public class GetEnterpriseServiceNonCustomServiceResponseFactory {

    public static GetEnterpriseServiceNonCustomServiceResponse fromDomain(
            NonCustomEnterpriseService service,
            Enterprise enterprise,
            List<NonCustomSlotTemplate> slots) {
        org.SwaggerCodeGenExample.model.Location swaggerLocation = LocationMapper.fromDomain(service.location());

        List<Slot> swaggerSlots = slots.stream()
                .map(SlotMapper::fromNonCustomSlotTemplate)
                .collect(Collectors.toList());

        return new GetEnterpriseServiceNonCustomServiceResponse(
                false,
                enterprise.enterpriseId(),
                enterprise.name(),
                service.enterpriseServiceId(),
                service.name(),
                service.description(),
                swaggerLocation,
                service.timezone().toString(),
                service.cathegory().toString(),
                service.price(),
                service.currency().toString(),
                swaggerSlots);
    }

}
