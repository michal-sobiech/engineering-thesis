package pl.michal_sobiech.core.enterprise_service.custom_appointments;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Optional;

import pl.michal_sobiech.core.currency_iso.CurrencyIso;
import pl.michal_sobiech.core.enterprise_service.EnterpriseServiceCathegory;
import pl.michal_sobiech.core.location.Location;

public record CreateCustomAppointmentsEnterpriseServiceCommand(

        String name,

        String description,

        Location location,

        ZoneId timeZone,

        Double maxDistanceKm,

        EnterpriseServiceCathegory cathegory,

        Optional<BigDecimal> price,

        CurrencyIso currency

) {
}
