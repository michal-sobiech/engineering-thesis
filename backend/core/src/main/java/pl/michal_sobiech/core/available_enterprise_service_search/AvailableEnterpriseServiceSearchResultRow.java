package pl.michal_sobiech.core.available_enterprise_service_search;

import java.math.BigDecimal;

public record AvailableEnterpriseServiceSearchResultRow(

        long enterpriseServiceId,

        String enterpriseServiceName,

        String enterpriseName,

        String address,

        BigDecimal price

) {

}
