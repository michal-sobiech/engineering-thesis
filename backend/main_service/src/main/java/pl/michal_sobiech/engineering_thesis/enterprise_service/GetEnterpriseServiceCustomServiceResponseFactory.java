package pl.michal_sobiech.engineering_thesis.enterprise_service;

import org.SwaggerCodeGenExample.model.GetEnterpriseServiceCustomServiceResponse;

import pl.michal_sobiech.core.enterprise.Enterprise;
import pl.michal_sobiech.core.enterprise_service.custom_appointments.CustomEnterpriseService;
import pl.michal_sobiech.engineering_thesis.api.LocationMapper;

public class GetEnterpriseServiceCustomServiceResponseFactory {

    public static GetEnterpriseServiceCustomServiceResponse fromDomain(
            CustomEnterpriseService service,
            Enterprise enterprise) {
        org.SwaggerCodeGenExample.model.Location swaggerLocation = LocationMapper.fromDomain(service.location());

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
                service.currency().toString());

    }

}
