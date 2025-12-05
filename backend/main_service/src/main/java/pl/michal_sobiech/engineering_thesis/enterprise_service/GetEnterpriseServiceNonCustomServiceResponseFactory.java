package pl.michal_sobiech.engineering_thesis.enterprise_service;

import org.SwaggerCodeGenExample.model.GetEnterpriseServiceNonCustomServiceResponse;
import org.SwaggerCodeGenExample.model.Location;

import pl.michal_sobiech.core.enterprise_service.no_custom_appointments.NonCustomEnterpriseService;
import pl.michal_sobiech.engineering_thesis.api.LocationMapper;

public class GetEnterpriseServiceNonCustomServiceResponseFactory {

    public static GetEnterpriseServiceNonCustomServiceResponse fromDomain(NonCustomEnterpriseService domain) {
        Location swaggerLocation = LocationMapper.fromDomain(domain.location());

        return new GetEnterpriseServiceNonCustomServiceResponse(
                false,
                domain.enterpriseServiceId(),
                domain.name(),
                domain.description(),
                swaggerLocation,
                domain.timezone().toString(),
                domain.cathegory().toString(),
                domain.price(),
                domain.currency().toString());
    }

}
