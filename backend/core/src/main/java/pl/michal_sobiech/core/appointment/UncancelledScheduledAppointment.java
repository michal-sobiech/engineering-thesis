package pl.michal_sobiech.core.appointment;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import pl.michal_sobiech.core.currency_iso.CurrencyIso;
import pl.michal_sobiech.core.location.Location;
import pl.michal_sobiech.core.location.Position;
import pl.michal_sobiech.core.payment.payment_status.PaymentStatus;

public record UncancelledScheduledAppointment(

        long appointmentId,
        long enterpriseServiceId,
        Long customerUserId,
        BigDecimal price,
        CurrencyIso currency,
        Instant startInstant,
        Instant endInstant,
        Location location,
        PaymentStatus paymentStatus

) {

    public static boolean matchesEntity(AppointmentEntity entity) {
        boolean isCustomScheduledAppointment = (entity.isCustom()
                && entity.getIsAccepted() != null && entity.getIsAccepted() == true
                && entity.getRejectionMessage() == null
                && !entity.isCancelled());

        boolean isNonCustomScheduledAppointment = (!entity.isCustom()
                && entity.getIsAccepted() == null
                && entity.getRejectionMessage() == null
                && !entity.isCancelled());

        return isCustomScheduledAppointment || isNonCustomScheduledAppointment;
    }

    public static Optional<UncancelledScheduledAppointment> fromEntity(AppointmentEntity entity) {
        if (!matchesEntity(entity)) {
            return Optional.empty();
        }

        PaymentStatus paymentStatus = PaymentStatus.of(
                entity.isPaid(),
                Optional.of(entity.getPaymentServiceProvider()),
                Optional.of(entity.getPspReference()),
                Optional.of(entity.getWasPayoutProcessed()));

        Location location = new Location(
                entity.getAddress(),
                new Position(
                        entity.getLongitude(),
                        entity.getLatitude()));

        var domain = new UncancelledScheduledAppointment(
                entity.getAppointmentId(),
                entity.getEnterpriseServiceId(),
                entity.getCustomerUserId(),
                entity.getPrice(),
                entity.getCurrency(),
                entity.getStartTime().toInstant(),
                entity.getEndTime().toInstant(),
                location,
                paymentStatus);

        return Optional.of(domain);
    }

}
