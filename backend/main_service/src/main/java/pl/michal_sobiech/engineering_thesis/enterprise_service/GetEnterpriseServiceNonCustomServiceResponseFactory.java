package pl.michal_sobiech.engineering_thesis.enterprise_service;

import org.SwaggerCodeGenExample.model.GetEnterpriseServiceNonCustomServiceResponse;

import pl.michal_sobiech.core.enterprise.Enterprise;
import pl.michal_sobiech.core.enterprise_service.no_custom_appointments.NonCustomEnterpriseService;
import pl.michal_sobiech.engineering_thesis.api.LocationMapper;

public class GetEnterpriseServiceNonCustomServiceResponseFactory {

    public static GetEnterpriseServiceNonCustomServiceResponse fromDomain(
            NonCustomEnterpriseService service,
            Enterprise enterprise) {
        org.SwaggerCodeGenExample.model.Location swaggerLocation = LocationMapper.fromDomain(service.location());

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
                service.currency().toString());
    }

}
