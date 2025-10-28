package pl.michal_sobiech.engineering_thesis.enterprise_service.custom_appointments_enterprise_service;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.List;

import org.SwaggerCodeGenExample.model.Location;

import pl.michal_sobiech.engineering_thesis.currency_iso.CurrencyIso;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceCathegory;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot.CreateEnterpriseServiceSlotCommand;

public record CreateCustomAppointmentsEnterpriseServiceCommand(

                String name,

                String description,

                Location location,

                ZoneId timeZone,

                Double maxDistanceKm,

                EnterpriseServiceCathegory cathegory,

                BigDecimal price,

                CurrencyIso currency,

                List<CreateEnterpriseServiceSlotCommand> slots

) {
}
