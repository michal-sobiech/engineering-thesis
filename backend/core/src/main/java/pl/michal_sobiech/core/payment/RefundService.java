package pl.michal_sobiech.core.payment;

import org.javamoney.moneta.Money;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.adyen.AdyenRefundService;

@RequiredArgsConstructor
public class RefundService {

    private final AdyenRefundService adyenRefundService;

    public void processRefund(PaymentServiceProvider paymentServiceProvider, String pspReference, Money amount) {
        switch (paymentServiceProvider) {
            case ADYEN -> {
                adyenRefundService.processRefund(pspReference, amount);
            }
        }
    }

}
