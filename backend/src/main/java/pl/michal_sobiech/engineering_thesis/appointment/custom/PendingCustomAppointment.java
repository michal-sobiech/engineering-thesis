package pl.michal_sobiech.engineering_thesis.appointment.custom;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

public record PendingCustomAppointment(

        long appointmentId,
        long enterpriseServiceId,
        Optional<Long> customerUserId,

        Optional<BigDecimal> price,
        OffsetDateTime startTime,
        OffsetDateTime endTime

) {
}
