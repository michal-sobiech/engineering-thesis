package pl.michal_sobiech.core.enterprise_service.no_custom_appointments;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Optional;

import pl.michal_sobiech.core.currency_iso.CurrencyIso;
import pl.michal_sobiech.core.enterprise_service.EnterpriseServiceCathegory;
import pl.michal_sobiech.core.location.Location;

public record CreateNoCustomAppointmentsEnterpriseServiceCommand(

        String name,

        String description,

        Location location,

        ZoneId timeZone,

        EnterpriseServiceCathegory cathegory,

        Optional<BigDecimal> price,

        CurrencyIso currency

) {
}
