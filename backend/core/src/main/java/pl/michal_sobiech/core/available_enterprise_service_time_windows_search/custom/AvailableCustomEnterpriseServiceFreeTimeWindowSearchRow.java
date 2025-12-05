package pl.michal_sobiech.core.available_enterprise_service_time_windows_search.custom;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;

public record AvailableCustomEnterpriseServiceFreeTimeWindowSearchRow(

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