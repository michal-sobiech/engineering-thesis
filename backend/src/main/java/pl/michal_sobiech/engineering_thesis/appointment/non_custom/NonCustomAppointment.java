package pl.michal_sobiech.engineering_thesis.appointment.non_custom;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

import pl.michal_sobiech.engineering_thesis.appointment.AppointmentEntity;

public record NonCustomAppointment(

        long appointmentId,
        long enterpriseServiceId,
        long customerUserId,

        Optional<BigDecimal> price,
        OffsetDateTime startDatetime,
        OffsetDateTime endDatetime

) {

    public static NonCustomAppointment fromEntity(AppointmentEntity entity) {
        if (entity.getIsCustom()) {
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
                Optional.ofNullable(entity.getPrice()),
                entity.getStartTime(),
                entity.getEndTime());
    }

}
