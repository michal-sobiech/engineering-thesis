package pl.michal_sobiech.engineering_thesis.appointment.non_custom;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

public record NonCustomAppointment(

        long appointmentId,
        long enterpriseServiceId,
        long customerUserId,

        Optional<BigDecimal> price,
        OffsetDateTime startTime,
        OffsetDateTime endTime

) {
}
