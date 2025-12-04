package pl.michal_sobiech.shared.appointment.custom;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import org.SwaggerCodeGenExample.model.Location;

import pl.michal_sobiech.engineering_thesis.currency_iso.CurrencyIso;
import pl.michal_sobiech.shared.appointment.AppointmentEntity;

public record RejectedAppointment(

        long appointmentId,
        long enterpriseServiceId,
        long customerUserId,
        BigDecimal price,
        CurrencyIso currency,
        Instant startInstant,
        Instant endInstant,
        Location location,

        String rejectionMessage

) {

    public static boolean matchesEntity(AppointmentEntity entity) {
        if (!entity.isCustom()) {
            return false;
        }

        if (entity.getIsAccepted() == null || entity.getIsAccepted() == true) {
            return false;
        }

        if (entity.getRejectionMessage() == null) {
            return false;
        }

        return true;
    }

    public static Optional<RejectedAppointment> fromEntity(AppointmentEntity entity) {
        if (!matchesEntity(entity)) {
            return Optional.empty();
        }

        Location location = new Location(
                entity.getAddress(),
                entity.getLongitude(),
                entity.getLatitude());

        var domain = new RejectedAppointment(
                entity.getAppointmentId(),
                entity.getEnterpriseServiceId(),
                entity.getCustomerUserId(),
                entity.getPrice(),
                entity.getCurrency(),
                entity.getStartTime().toInstant(),
                entity.getEndTime().toInstant(),
                location,
                entity.getRejectionMessage());
        return Optional.of(domain);
    }

}
