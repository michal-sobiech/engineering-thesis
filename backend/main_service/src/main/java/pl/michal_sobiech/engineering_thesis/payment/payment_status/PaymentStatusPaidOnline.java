package pl.michal_sobiech.engineering_thesis.payment.payment_status;

import pl.michal_sobiech.engineering_thesis.payment.PaymentServiceProvider;

public record PaymentStatusPaidOnline(

        PaymentServiceProvider paymentServiceProvider,

        String pspReference,

        boolean wasPayoutProcessed

) implements PaymentStatus {

}
