package pl.michal_sobiech.core.appointment.custom;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import pl.michal_sobiech.core.appointment.AppointmentEntity;
import pl.michal_sobiech.core.currency_iso.CurrencyIso;
import pl.michal_sobiech.core.location.Location;
import pl.michal_sobiech.core.location.Position;

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
                new Position(
                        entity.getLongitude(),
                        entity.getLatitude()));

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
