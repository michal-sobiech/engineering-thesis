package pl.michal_sobiech.core.appointment.custom;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import pl.michal_sobiech.core.appointment.AppointmentEntity;
import pl.michal_sobiech.core.currency_iso.CurrencyIso;
import pl.michal_sobiech.core.location.Location;

public record UncancelledPendingAppointment(

        long appointmentId,
        long enterpriseServiceId,
        long customerUserId,
        BigDecimal price,
        CurrencyIso currency,
        Instant startInstant,
        Instant endInstant,
        Location location

) implements CustomAppointment {

    private static boolean matchesEntity(AppointmentEntity entity) {
        return (entity.isCustom()
                && entity.getIsAccepted() == null
                && entity.getRejectionMessage() == null
                && !entity.isCancelled());
    }

    public static Optional<UncancelledPendingAppointment> fromEntity(AppointmentEntity entity) {
        if (!matchesEntity(entity)) {
            return Optional.empty();
        }

        Location location = new Location(
                entity.getAddress(),
                entity.getLongitude(),
                entity.getLatitude());

        var domain = new UncancelledPendingAppointment(
                entity.getAppointmentId(),
                entity.getEnterpriseServiceId(),
                entity.getCustomerUserId(),
                entity.getPrice(),
                entity.getCurrency(),
                entity.getStartTime().toInstant(),
                entity.getEndTime().toInstant(),
                location);
        return Optional.of(domain);
    }

}
