package pl.michal_sobiech.core.appointment.non_custom;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record NonCustomAppointmentSlot(

        BigDecimal numVacancies,
        OffsetDateTime startDatetime,
        OffsetDateTime endDatetime

) {

}
