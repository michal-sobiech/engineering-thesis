package pl.michal_sobiech.engineering_thesis.appointment.custom.rejected;

import java.math.BigDecimal;
import java.time.Instant;

import org.SwaggerCodeGenExample.model.Location;

import pl.michal_sobiech.engineering_thesis.appointment.AppointmentEntity;

public record RejectedCustomAppointment(

        Long appointmentId,
        Long enterpriseServiceId,
        Long customerUserId,

        BigDecimal price,
        Instant startInstant,
        Instant endInstant,
        Location location,

        String rejectionMessage

) {

    public static RejectedCustomAppointment fromEntity(AppointmentEntity entity) {
        if (!entity.getIsCustom()) {
            throw new IllegalArgumentException();
        }

        if (entity.getIsAccepted() != null || entity.getIsAccepted() == true) {
            throw new IllegalArgumentException();
        }

        if (entity.getRejectionMessage() == null) {
            throw new IllegalArgumentException();
        }

        Location location = new Location(
                entity.getAddress(),
                entity.getLongitude(),
                entity.getLatitude());

        return new RejectedCustomAppointment(
                entity.getAppointmentId(),
                entity.getEnterpriseServiceId(),
                entity.getCustomerUserId(),
                entity.getPrice(),
                entity.getStartTime().toInstant(),
                entity.getEndTime().toInstant(),
                location,
                entity.getRejectionMessage());

    }

}
