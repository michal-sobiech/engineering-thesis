package pl.michal_sobiech.core.enterprise_service.custom_appointments;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Optional;

import org.SwaggerCodeGenExample.model.Location;

import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceCathegory;
import pl.michal_sobiech.shared.currency_iso.CurrencyIso;

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
