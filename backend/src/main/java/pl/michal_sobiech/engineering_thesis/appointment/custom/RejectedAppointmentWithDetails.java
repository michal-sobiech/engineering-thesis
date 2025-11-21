package pl.michal_sobiech.engineering_thesis.appointment.custom;

import java.math.BigDecimal;
import java.time.Instant;

import org.SwaggerCodeGenExample.model.Location;

import pl.michal_sobiech.engineering_thesis.currency_iso.CurrencyIso;

public record RejectedAppointmentWithDetails(

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

        Location location,

        String rejectionMessage

) implements CustomAppointmentWithDetails {
}
