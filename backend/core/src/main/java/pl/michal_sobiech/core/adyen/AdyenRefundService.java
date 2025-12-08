package pl.michal_sobiech.core.adyen;

import java.io.IOException;

import org.javamoney.moneta.Money;

import com.adyen.model.checkout.Amount;
import com.adyen.model.checkout.PaymentRefundRequest;
import com.adyen.service.checkout.ModificationsApi;
import com.adyen.service.exception.ApiException;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.money.MoneyUtils;

@RequiredArgsConstructor
public class AdyenRefundService {

    private final ModificationsApi modificationsApi;
    private final AdyenProperties adyenProperties;

    public void processRefund(String pspReference, Money amount) {
        long amountMinorUnits = MoneyUtils.getLongMinorUnitsNumber(amount);

        Amount adyenAmount = new Amount()
                .currency(amount.getCurrency().toString())
                .value(amountMinorUnits);

        PaymentRefundRequest refundRequest = new PaymentRefundRequest()
                .merchantAccount(adyenProperties.merchantAccount())
                .amount(adyenAmount)
                .reference(pspReference);

        try {
            modificationsApi.refundCapturedPayment(pspReference, refundRequest);
        } catch (ApiException | IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}
