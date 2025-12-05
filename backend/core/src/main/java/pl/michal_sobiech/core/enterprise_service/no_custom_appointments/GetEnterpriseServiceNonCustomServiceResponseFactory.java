package pl.michal_sobiech.core.enterprise_service.no_custom_appointments;

import org.SwaggerCodeGenExample.model.GetEnterpriseServiceNonCustomServiceResponse;

public class GetEnterpriseServiceNonCustomServiceResponseFactory {

    public static GetEnterpriseServiceNonCustomServiceResponse fromDomain(NonCustomEnterpriseService domain) {
        return new GetEnterpriseServiceNonCustomServiceResponse(
                false,
                domain.enterpriseServiceId(),
                domain.name(),
                domain.description(),
                domain.location(),
                domain.timezone().toString(),
                domain.cathegory().toString(),
                domain.price(),
                domain.currency().toString());
    }

}
