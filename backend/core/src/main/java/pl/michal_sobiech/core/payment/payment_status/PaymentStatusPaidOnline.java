package pl.michal_sobiech.core.payment.payment_status;

import pl.michal_sobiech.core.payment.PaymentServiceProvider;

public record PaymentStatusPaidOnline(

        PaymentServiceProvider paymentServiceProvider,

        String pspReference,

        boolean wasPayoutProcessed

) implements PaymentStatus {

}
