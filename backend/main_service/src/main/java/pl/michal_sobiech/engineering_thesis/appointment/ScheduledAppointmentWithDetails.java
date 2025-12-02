package pl.michal_sobiech.engineering_thesis.appointment;

import java.math.BigDecimal;
import java.time.Instant;

import org.SwaggerCodeGenExample.model.Location;

import pl.michal_sobiech.engineering_thesis.currency_iso.CurrencyIso;
import pl.michal_sobiech.engineering_thesis.payment.payment_status.PaymentStatus;

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
