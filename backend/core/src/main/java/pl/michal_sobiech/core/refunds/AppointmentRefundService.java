package pl.michal_sobiech.core.refunds;

import org.javamoney.moneta.Money;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.appointment.AppointmentEntity;
import pl.michal_sobiech.core.appointment.AppointmentService;
import pl.michal_sobiech.core.payment.PaymentServiceProvider;
import pl.michal_sobiech.core.payment.RefundService;

@RequiredArgsConstructor
public class AppointmentRefundService {

    private final AppointmentService appointmentService;
    private final RefundService refundService;

    public void processCancelledScheduledFutureAppointmentRefund(
            long appointmentId,
            PaymentServiceProvider paymentServiceProvider) {
        // TODO maybe domain objects?
        AppointmentEntity appointment = appointmentService.getById(appointmentId).orElseThrow();
        Money amount = Money.of(
                appointment.getPrice(),
                appointment.getCurrency().toString());
        refundService.processRefund(
                appointment.getPaymentServiceProvider(),
                appointment.getPspReference(),
                amount);
    }

}
