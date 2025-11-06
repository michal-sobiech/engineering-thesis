package pl.michal_sobiech.engineering_thesis.appointment;

import java.math.BigDecimal;
import java.time.Instant;

import org.SwaggerCodeGenExample.model.Location;

public record ScheduledAppointment(

        Long appointmentId,
        Long enterpriseServiceId,
        Long customerUserId,

        BigDecimal price,
        Instant startInstant,
        Instant endInstant,
        Location location

) {

}
