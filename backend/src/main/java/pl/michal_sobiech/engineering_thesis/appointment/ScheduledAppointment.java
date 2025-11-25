package pl.michal_sobiech.engineering_thesis.appointment;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import org.SwaggerCodeGenExample.model.Location;

import pl.michal_sobiech.engineering_thesis.currency_iso.CurrencyIso;

// TODO cancelled or not?
public record ScheduledAppointment(

        long appointmentId,
        long enterpriseServiceId,
        Long customerUserId,
        BigDecimal price,
        CurrencyIso currency,
        Instant startInstant,
        Instant endInstant,
        Location location

) {

    public static boolean matchesEntity(AppointmentEntity entity) {
        boolean isCustomScheduledAppointment = (entity.isCustom()
                && entity.getIsAccepted() != null && entity.getIsAccepted() == true
                && entity.getRejectionMessage() == null);

        boolean isNonCustomScheduledAppointment = (!entity.isCustom()
                && entity.getIsAccepted() == null
                && entity.getRejectionMessage() == null);

        return isCustomScheduledAppointment || isNonCustomScheduledAppointment;
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
                entity.getCurrency(),
                entity.getStartTime().toInstant(),
                entity.getEndTime().toInstant(),
                location);

        return Optional.of(domain);
    }

}
