package pl.michal_sobiech.engineering_thesis.appointment.custom;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

import pl.michal_sobiech.engineering_thesis.appointment.AppointmentEntity;

public record ConfirmedCustomAppointment(

        long appointmentId,
        long enterpriseServiceId,
        Long customerUserId,

        Optional<BigDecimal> price,
        OffsetDateTime startTime,
        OffsetDateTime endTime

) {

    public static ConfirmedCustomAppointment fromEntity(AppointmentEntity entity) {
        if (entity.getIsCustom() == false) {
            throw new IllegalArgumentException();
        }

        if (entity.getIsAccepted() == false) {
            throw new IllegalArgumentException();
        }

        if (entity.getRejectionMessage() != null) {
            throw new IllegalArgumentException();
        }

        return new ConfirmedCustomAppointment(
                entity.getAppointmentId(),
                entity.getEnterpriseServiceId(),
                entity.getCustomerUserId(),
                Optional.ofNullable(entity.getPrice()),
                entity.getStartTime(),
                entity.getEndTime());
    }

}
