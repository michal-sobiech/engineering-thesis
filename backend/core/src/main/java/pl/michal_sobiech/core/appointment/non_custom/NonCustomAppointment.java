package pl.michal_sobiech.shared.appointment.non_custom;

import java.math.BigDecimal;
import java.time.Instant;

import pl.michal_sobiech.shared.appointment.AppointmentEntity;

public record NonCustomAppointment(

        long appointmentId,
        long enterpriseServiceId,
        long customerUserId,

        BigDecimal price,
        Instant startInstant,
        Instant endInstant

) {

    public static NonCustomAppointment fromEntity(AppointmentEntity entity) {
        if (entity.isCustom()) {
            throw new IllegalArgumentException();
        }

        if (entity.getIsAccepted() != null) {
            throw new IllegalStateException();
        }

        if (entity.getRejectionMessage() != null) {
            throw new IllegalStateException();
        }

        return new NonCustomAppointment(
                entity.getAppointmentId(),
                entity.getEnterpriseServiceId(),
                entity.getCustomerUserId(),
                entity.getPrice(),
                entity.getStartTime().toInstant(),
                entity.getEndTime().toInstant());
    }

}
