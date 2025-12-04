package pl.michal_sobiech.shared.appointment.custom;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import org.SwaggerCodeGenExample.model.Location;

import pl.michal_sobiech.shared.appointment.AppointmentEntity;
import pl.michal_sobiech.shared.currency_iso.CurrencyIso;

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
        System.out.println(entity);
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
