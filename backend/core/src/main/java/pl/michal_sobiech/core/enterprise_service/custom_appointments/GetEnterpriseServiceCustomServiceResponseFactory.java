package pl.michal_sobiech.core.enterprise_service.custom_appointments;

import org.SwaggerCodeGenExample.model.GetEnterpriseServiceCustomServiceResponse;

public class GetEnterpriseServiceCustomServiceResponseFactory {

    public static GetEnterpriseServiceCustomServiceResponse fromDomain(CustomEnterpriseService domain) {
        return new GetEnterpriseServiceCustomServiceResponse(
                true,
                domain.enterpriseServiceId(),
                domain.name(),
                domain.description(),
                domain.location(),
                domain.timezone().toString(),
                domain.maxDistanceKm(),
                domain.cathegory().toString(),
                domain.price(),
                domain.currency().toString());
    }

}
