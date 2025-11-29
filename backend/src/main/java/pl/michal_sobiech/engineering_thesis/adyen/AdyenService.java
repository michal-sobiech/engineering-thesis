package pl.michal_sobiech.engineering_thesis.adyen;

import java.math.BigDecimal;
import java.net.URL;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import com.adyen.model.RequestOptions;
import com.adyen.model.checkout.Amount;
import com.adyen.model.checkout.CreateCheckoutSessionRequest;
import com.adyen.model.checkout.CreateCheckoutSessionResponse;
import com.adyen.service.checkout.PaymentsApi;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.appointment.AppointmentService;
import pl.michal_sobiech.engineering_thesis.currency_iso.CurrencyIso;
import pl.michal_sobiech.engineering_thesis.payment.MerchantReferenceUtils;
import pl.michal_sobiech.engineering_thesis.payment.PaymentSubjectType;

@Service
@RequiredArgsConstructor
public class AdyenService {

    private final AdyenProperties adyenProperties;
    private final PaymentsApi adyenPaymentsApi;
    private final AppointmentService appointmentService;

    public CreateCheckoutSessionResponse createSession(
            PaymentSubjectType paymentSubjectType,
            long paymentSubjectId,
            URL returnUrl) {
        Pair<Long, String> priceAndCurrency = getPriceAndCurrencyOfPaymentSubject(paymentSubjectType, paymentSubjectId);

        Amount amount = new Amount();
        amount.setValue(priceAndCurrency.getFirst());
        amount.setCurrency(priceAndCurrency.getSecond().toString());

        String merchantReference = MerchantReferenceUtils.createMerchantReference(paymentSubjectType, paymentSubjectId);

        CreateCheckoutSessionRequest request = new CreateCheckoutSessionRequest()
                .reference(merchantReference)
                .amount(amount)
                .merchantAccount(adyenProperties.merchantAccount())
                .returnUrl(returnUrl.toString());

        try {
            return adyenPaymentsApi.sessions(
                    request,
                    new RequestOptions().idempotencyKey("UUID"));
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private Pair<Long, String> getPriceAndCurrencyOfPaymentSubject(
            PaymentSubjectType paymentSubjectType,
            long paymentSubjectId) {
        return switch (paymentSubjectType) {
            case APPOINTMENT -> {
                Pair<BigDecimal, CurrencyIso> priceAndCurrency = appointmentService
                        .getAppointmentPriceAndCurrency(paymentSubjectId);
                long priceMinorUnits = priceAndCurrency.getFirst().multiply(BigDecimal.valueOf(100)).longValue();
                String currency = priceAndCurrency.getSecond().toString();
                yield Pair.of(priceMinorUnits, currency);
            }
        };
    }

}
