package pl.michal_sobiech.core.appointment;

import java.math.BigDecimal;
import java.time.Instant;

import pl.michal_sobiech.core.currency_iso.CurrencyIso;
import pl.michal_sobiech.core.location.Location;
import pl.michal_sobiech.core.payment.payment_status.PaymentStatus;

public record ScheduledAppointmentWithDetails(

        long appointmentId,

        long enterpriseServiceId,

        long customerUserId,

        String customerUsername,

        String customerFirstName,

        String customerLastName,

        BigDecimal price,

        CurrencyIso currency,

        Instant startInstant,

        Instant endInstant,

        Location location,

        PaymentStatus paymentStatus

) {
}
