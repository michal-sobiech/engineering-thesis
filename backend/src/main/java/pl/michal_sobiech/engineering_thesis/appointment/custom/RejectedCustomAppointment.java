package pl.michal_sobiech.engineering_thesis.appointment.custom;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

public record RejectedCustomAppointment(

                long appointmentId,
                long enterpriseServiceId,
                Long customerUserId,

                Optional<BigDecimal> price,
                OffsetDateTime startTime,
                OffsetDateTime endTime,
                String rejectionMessage

) {

}
