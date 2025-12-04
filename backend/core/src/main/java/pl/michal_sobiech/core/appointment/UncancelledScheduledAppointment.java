package pl.michal_sobiech.shared.appointment;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import org.SwaggerCodeGenExample.model.Location;

import pl.michal_sobiech.shared.currency_iso.CurrencyIso;
import pl.michal_sobiech.shared.payment.payment_status.PaymentStatus;

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

                Location location = new Location(
                                entity.getAddress(),
                                entity.getLongitude(),
                                entity.getLatitude());

                PaymentStatus paymentStatus = PaymentStatus.of(
                                entity.isPaid(),
                                Optional.of(entity.getPaymentServiceProvider()),
                                Optional.of(entity.getPspReference()),
                                Optional.of(entity.getWasPayoutProcessed()));

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
