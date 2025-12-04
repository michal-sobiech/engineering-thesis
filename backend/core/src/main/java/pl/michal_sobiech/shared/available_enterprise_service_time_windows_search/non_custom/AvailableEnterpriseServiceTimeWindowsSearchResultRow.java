package pl.michal_sobiech.engineering_thesis.available_enterprise_service_time_windows_search.non_custom;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;

public record AvailableEnterpriseServiceTimeWindowsSearchResultRow(

        Long enterpriseServiceId,

        String enterpriseServiceName,

        String enterpriseName,

        String address,

        BigDecimal price,

        ZoneId timezone,

        Instant start,

        Instant end

) {

}
