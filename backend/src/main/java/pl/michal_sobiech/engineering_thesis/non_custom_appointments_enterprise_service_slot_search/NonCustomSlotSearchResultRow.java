package pl.michal_sobiech.engineering_thesis.non_custom_appointments_enterprise_service_slot_search;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;

public record NonCustomSlotSearchResultRow(

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
