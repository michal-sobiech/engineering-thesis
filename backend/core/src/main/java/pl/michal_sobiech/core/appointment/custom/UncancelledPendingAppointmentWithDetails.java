package pl.michal_sobiech.core.appointment.custom;

import java.math.BigDecimal;
import java.time.Instant;

import pl.michal_sobiech.core.currency_iso.CurrencyIso;
import pl.michal_sobiech.core.location.Location;

public record UncancelledPendingAppointmentWithDetails(

        long appointmentId,

        long enterpriseServiceId,

        long customerUserId,

        String getCustomerUsername,

        String getCustomerFirstName,

        String getCustomerLastName,

        BigDecimal price,

        CurrencyIso currency,

        Instant startInstant,

        Instant endInstant,

        Location location

) implements CustomAppointmentWithDetails {
}