package pl.michal_sobiech.engineering_thesis.appointment.custom;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import org.SwaggerCodeGenExample.model.Location;

import pl.michal_sobiech.engineering_thesis.appointment.AppointmentEntity;

public record ScheduledAppointment(

        long appointmentId,
        long enterpriseServiceId,
        Long customerUserId,

        BigDecimal price,
        Instant startInstant,
        Instant endInstant,
        Location location

) {

    public static boolean matchesEntity(AppointmentEntity entity) {
        if (entity.isCustom() == false) {
            return false;
        }

        if (entity.getIsAccepted() == false) {
            return false;
        }

        if (entity.getRejectionMessage() != null) {
            return false;
        }

        return true;
    }

    public static Optional<ScheduledAppointment> fromEntity(AppointmentEntity entity) {
        if (!matchesEntity(entity)) {
            return Optional.empty();
        }

        Location location = new Location(
                entity.getAddress(),
                entity.getLongitude(),
                entity.getLatitude());

        var domain = new ScheduledAppointment(
                entity.getAppointmentId(),
                entity.getEnterpriseServiceId(),
                entity.getCustomerUserId(),
                entity.getPrice(),
                entity.getStartTime().toInstant(),
                entity.getEndTime().toInstant(),
                location);

        return Optional.of(domain);
    }

}
