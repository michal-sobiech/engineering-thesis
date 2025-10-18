package pl.michal_sobiech.engineering_thesis.enterprise_service;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Currency;
import java.util.Optional;

public record CreateEnterpriseServiceCommand(

        String name,

        String description,

        Optional<String> location,

        ZoneId timeZone,

        boolean takesCustomAppointments,

        BigDecimal price,

        Currency currency

) {
}
