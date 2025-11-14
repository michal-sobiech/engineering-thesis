package pl.michal_sobiech.engineering_thesis.available_enterprise_service_search;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.lang.Nullable;

public record SearchEnterpriseServicesCommand(
        Optional<String> serviceName,
        Optional<String> enterpriseName,
        Optional<OffsetDateTime> startDate,
        Optional<OffsetDateTime> endDate,
        BigDecimal preferredLongitude,
        BigDecimal preferredLatitude,
        String cathegory,
        Double maxDistanceKm) {

    public static SearchEnterpriseServicesCommand fromRaw(
            @Nullable String serviceName,
            @Nullable String enterpriseName,
            @Nullable OffsetDateTime startDate,
            @Nullable OffsetDateTime endDate,
            BigDecimal preferredLongitude,
            BigDecimal preferredLatitude,
            String cathegory,
            double maxDistanceKm) {
        return new SearchEnterpriseServicesCommand(
                Optional.ofNullable(serviceName),
                Optional.ofNullable(enterpriseName),
                Optional.ofNullable(startDate),
                Optional.ofNullable(endDate),
                preferredLongitude,
                preferredLatitude,
                cathegory,
                maxDistanceKm);
    }

}
