package pl.michal_sobiech.core.payment.payment_status;

import java.util.Optional;

import pl.michal_sobiech.core.payment.PaymentServiceProvider;

public interface PaymentStatus {

    public static PaymentStatus of(
            boolean isPaid,
            Optional<PaymentServiceProvider> paymentServiceProvider,
            Optional<String> pspReference,
            Optional<Boolean> wasPayoutProcessed) {
        if (!isPaid) {
            return new PaymentStatusNotPaid();
        }

        if (paymentServiceProvider.isPresent() && pspReference.isPresent() && wasPayoutProcessed.isPresent()) {
            return new PaymentStatusPaidOnline(paymentServiceProvider.get(), pspReference.get(),
                    wasPayoutProcessed.get());
        }

        if (paymentServiceProvider.isEmpty() && pspReference.isEmpty() && wasPayoutProcessed.isEmpty()) {
            return new PaymentStatusPaidOnSite();
        }

        throw new IllegalArgumentException();
    }

}
