package pl.michal_sobiech.engineering_thesis.enterprise_service;

import org.SwaggerCodeGenExample.model.GetEnterpriseServiceCustomServiceResponse;
import org.SwaggerCodeGenExample.model.Location;

import pl.michal_sobiech.core.enterprise_service.custom_appointments.CustomEnterpriseService;

public class GetEnterpriseServiceCustomServiceResponseFactory {

    public static GetEnterpriseServiceCustomServiceResponse fromDomain(CustomEnterpriseService domain) {
        Location swaggerLocation = new Location(
                domain.location().address(),
                domain.location().longitude(),
                domain.location().latitude());

        return new GetEnterpriseServiceCustomServiceResponse(
                true,
                domain.enterpriseServiceId(),
                domain.name(),
                domain.description(),
                swaggerLocation,
                domain.timezone().toString(),
                domain.maxDistanceKm(),
                domain.cathegory().toString(),
                domain.price(),
                domain.currency().toString());
    }

}
