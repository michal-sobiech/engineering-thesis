package pl.michal_sobiech.engineering_thesis.appointment.custom;

import java.math.BigDecimal;
import java.time.Instant;

import org.SwaggerCodeGenExample.model.Location;

import pl.michal_sobiech.engineering_thesis.appointment.AppointmentEntity;

public record ConfirmedCustomAppointment(

        long appointmentId,
        long enterpriseServiceId,
        Long customerUserId,

        BigDecimal price,
        Instant startInstant,
        Instant endInstant,
        Location location

) {

    public static ConfirmedCustomAppointment fromEntity(AppointmentEntity entity) {
        if (entity.isCustom() == false) {
            throw new IllegalArgumentException();
        }

        if (entity.getIsAccepted() == false) {
            throw new IllegalArgumentException();
        }

        if (entity.getRejectionMessage() != null) {
            throw new IllegalArgumentException();
        }

        Location location = new Location(
                entity.getAddress(),
                entity.getLongitude(),
                entity.getLatitude());

        return new ConfirmedCustomAppointment(
                entity.getAppointmentId(),
                entity.getEnterpriseServiceId(),
                entity.getCustomerUserId(),
                entity.getPrice(),
                entity.getStartTime().toInstant(),
                entity.getEndTime().toInstant(),
                location);
    }

}
