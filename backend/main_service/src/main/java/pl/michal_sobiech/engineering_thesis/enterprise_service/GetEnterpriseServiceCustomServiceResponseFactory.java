package pl.michal_sobiech.engineering_thesis.enterprise_service;

import java.util.List;
import java.util.stream.Collectors;

import org.SwaggerCodeGenExample.model.GetEnterpriseServiceCustomServiceResponse;
import org.SwaggerCodeGenExample.model.TimeWindow;

import pl.michal_sobiech.core.enterprise.Enterprise;
import pl.michal_sobiech.core.enterprise_service.custom_appointments.CustomEnterpriseService;
import pl.michal_sobiech.core.enterprise_service_slot_template.custom_appointments.CustomSlotTemplate;
import pl.michal_sobiech.engineering_thesis.api.LocationMapper;
import pl.michal_sobiech.engineering_thesis.api.TimeWindowMapper;

public class GetEnterpriseServiceCustomServiceResponseFactory {

    public static GetEnterpriseServiceCustomServiceResponse fromDomain(
            CustomEnterpriseService service,
            Enterprise enterprise,
            List<CustomSlotTemplate> timeWindows) {
        org.SwaggerCodeGenExample.model.Location swaggerLocation = LocationMapper.fromDomain(service.location());

        List<TimeWindow> swaggerTimeWindows = timeWindows
                .stream()
                .map(TimeWindowMapper::fromCustomSlotTemplate)
                .collect(Collectors.toList());

        return new GetEnterpriseServiceCustomServiceResponse(
                true,
                enterprise.enterpriseId(),
                enterprise.name(),
                service.enterpriseServiceId(),
                service.name(),
                service.description(),
                swaggerLocation,
                service.timezone().toString(),
                service.maxDistanceKm(),
                service.cathegory().toString(),
                service.price(),
                service.currency().toString(),
                swaggerTimeWindows);

    }

}
