package pl.michal_sobiech.shared.payment.payment_status;

import pl.michal_sobiech.shared.payment.PaymentServiceProvider;

public record PaymentStatusPaidOnline(

                PaymentServiceProvider paymentServiceProvider,

                String pspReference,

                boolean wasPayoutProcessed

) implements PaymentStatus {

}
