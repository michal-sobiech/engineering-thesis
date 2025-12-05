package pl.michal_sobiech.core.enterprise_service.custom_appointments;

import java.math.BigDecimal;
import java.time.ZoneId;

import org.SwaggerCodeGenExample.model.Location;

import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceCathegory;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceDomain;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceEntity;
import pl.michal_sobiech.shared.currency_iso.CurrencyIso;

public record CustomEnterpriseService(

        long enterpriseServiceId,

        long enterpriseId,

        String name,
        String description,

        Location location,

        ZoneId timezone,

        Double maxDistanceKm,

        EnterpriseServiceCathegory cathegory,

        BigDecimal price,

        CurrencyIso currency,

        boolean suspendedByAdmin

) implements EnterpriseServiceDomain {

    public static CustomEnterpriseService fromEntity(EnterpriseServiceEntity entity) {
        if (entity.isTakesCustomAppointments() == false) {
            throw new IllegalArgumentException();
        }

        Location location = new Location(
                entity.getAddress(),
                entity.getLongitude(),
                entity.getLatitude());

        return new CustomEnterpriseService(
                entity.getEnterpriseServiceId(),
                entity.getEnterpriseId(),
                entity.getName(),
                entity.getDescription(),
                location,
                entity.getTimeZone(),
                entity.getMaxDistanceKm(),
                entity.getCathegory(),
                entity.getPrice(),
                entity.getCurrency(),
                entity.isSuspendedByAdmin());
    }

}
