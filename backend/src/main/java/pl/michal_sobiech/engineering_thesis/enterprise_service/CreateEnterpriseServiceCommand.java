package pl.michal_sobiech.engineering_thesis.enterprise_service;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

import org.SwaggerCodeGenExample.model.Location;

import pl.michal_sobiech.engineering_thesis.enterprise_service_slot.CreateEnterpriseServiceSlotCommand;

public record CreateEnterpriseServiceCommand(

                String name,

                String description,

                Optional<Location> location,

                ZoneId timeZone,

                List<CreateEnterpriseServiceSlotCommand> slots,

                boolean takesCustomAppointments,

                BigDecimal price,

                Currency currency

) {
}
