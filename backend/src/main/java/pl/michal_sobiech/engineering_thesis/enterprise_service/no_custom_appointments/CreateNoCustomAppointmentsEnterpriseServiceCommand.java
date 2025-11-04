package pl.michal_sobiech.engineering_thesis.enterprise_service.no_custom_appointments;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Optional;

import org.SwaggerCodeGenExample.model.Location;

import pl.michal_sobiech.engineering_thesis.currency_iso.CurrencyIso;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceCathegory;

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
