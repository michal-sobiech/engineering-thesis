package pl.michal_sobiech.engineering_thesis.enterprise_service_search;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.lang.Nullable;

public record SearchEnterpriseServicesCommand(
        Optional<String> serviceName,
        Optional<String> enterpriseName,
        Optional<OffsetDateTime> startDate,
        Optional<OffsetDateTime> endDate,
        Optional<BigDecimal> preferredLongitude,
        Optional<BigDecimal> preferredLatitude,
        Optional<String> cathegory) {

    public static SearchEnterpriseServicesCommand fromRaw(
            @Nullable String serviceName,
            @Nullable String enterpriseName,
            @Nullable OffsetDateTime startDate,
            @Nullable OffsetDateTime endDate,
            @Nullable BigDecimal preferredLongitude,
            @Nullable BigDecimal preferredLatitude,
            @Nullable String cathegory) {
        return new SearchEnterpriseServicesCommand(
                Optional.ofNullable(serviceName),
                Optional.ofNullable(enterpriseName),
                Optional.ofNullable(startDate),
                Optional.ofNullable(endDate),
                Optional.ofNullable(preferredLongitude),
                Optional.ofNullable(preferredLatitude),
                Optional.ofNullable(cathegory));
    }

}
