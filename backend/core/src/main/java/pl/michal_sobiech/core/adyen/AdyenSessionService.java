package pl.michal_sobiech.core.adyen;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import com.adyen.model.RequestOptions;
import com.adyen.model.checkout.Amount;
import com.adyen.model.checkout.CreateCheckoutSessionRequest;
import com.adyen.model.checkout.CreateCheckoutSessionResponse;
import com.adyen.model.checkout.Payment;
import com.adyen.model.checkout.Payment.ResultCodeEnum;
import com.adyen.model.checkout.SessionResultResponse;
import com.adyen.model.checkout.SessionResultResponse.StatusEnum;
import com.adyen.service.checkout.PaymentsApi;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.appointment.AppointmentService;
import pl.michal_sobiech.core.currency_iso.CurrencyIso;
import pl.michal_sobiech.core.payment.MerchantReferenceUtils;
import pl.michal_sobiech.core.payment.PaymentServiceProvider;
import pl.michal_sobiech.core.payment.PaymentSubjectType;

@RequiredArgsConstructor
public class AdyenSessionService {

    private final AdyenProperties adyenProperties;
    private final PaymentsApi adyenPaymentsApi;
    private final AppointmentService appointmentService;

    public CreateCheckoutSessionResponse createSession(
            PaymentSubjectType paymentSubjectType,
            long paymentSubjectId,
            URL returnUrl) {
        Pair<Long, String> priceAndCurrency = getPriceAndCurrencyOfPaymentSubject(paymentSubjectType, paymentSubjectId);

        Amount amount = new Amount()
                .value(priceAndCurrency.getLeft())
                .currency(priceAndCurrency.getRight().toString());

        String merchantReference = MerchantReferenceUtils.createMerchantReference(paymentSubjectType, paymentSubjectId);

        CreateCheckoutSessionRequest request = new CreateCheckoutSessionRequest()
                .reference(merchantReference)
                .mode(CreateCheckoutSessionRequest.ModeEnum.HOSTED)
                .amount(amount)
                .merchantAccount(adyenProperties.merchantAccount())
                .returnUrl(returnUrl.toString());

        try {
            return adyenPaymentsApi.sessions(
                    request,
                    new RequestOptions().idempotencyKey(UUID.randomUUID().toString()));
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public boolean handleSessionResult(String sessionId, String sessionResultToken) {
        // Returns whether session was successful

        SessionResultResponse sessionResult = getSessionResult(sessionId, sessionResultToken);
        boolean isSessionSuccessful = isSessionSuccessful(sessionResult);
        if (!isSessionSuccessful) {
            return false;
        }

        Payment successfulPayment = getSuccessfulPayment(sessionResult.getPayments()).orElseThrow();
        String merchantReference = sessionResult.getReference();
        handleSuccessfulSessionResult(merchantReference, successfulPayment);
        return true;
    }

    private Pair<Long, String> getPriceAndCurrencyOfPaymentSubject(
            PaymentSubjectType paymentSubjectType,
            long paymentSubjectId) {
        return switch (paymentSubjectType) {
            case APPOINTMENT -> {
                Pair<BigDecimal, CurrencyIso> priceAndCurrency = appointmentService
                        .getAppointmentPriceAndCurrency(paymentSubjectId);
                long priceMinorUnits = priceAndCurrency.getLeft().multiply(BigDecimal.valueOf(100)).longValue();
                String currency = priceAndCurrency.getRight().toString();
                yield Pair.of(priceMinorUnits, currency);
            }
        };
    }

    private void handleSuccessfulSessionResult(String merchantReference, Payment successfulPayment) {
        var paymentSubjectAndId = MerchantReferenceUtils.extractPaymentSubjectTypeAndId(merchantReference);
        PaymentSubjectType paymentSubjectType = paymentSubjectAndId.getLeft();
        long paymentSubjectId = paymentSubjectAndId.getRight();

        switch (paymentSubjectType) {
            case APPOINTMENT -> {
                appointmentService.markAppointmentAsPaidOnline(
                        paymentSubjectId,
                        PaymentServiceProvider.ADYEN,
                        successfulPayment.getPspReference());
            }
        }
    }

    public boolean fetchIsSessionSuccessful(String sessionId, String sessionResultToken) {
        SessionResultResponse sessionResult = getSessionResult(sessionId, sessionResultToken);
        return isSessionSuccessful(sessionResult);
    }

    public boolean isSessionSuccessful(SessionResultResponse sessionResult) {
        return (sessionResult.getStatus() == StatusEnum.COMPLETED
                &&
                isAnySessionPaymentsSuccessful(sessionResult.getPayments()));
    }

    private Optional<Payment> getSuccessfulPayment(List<Payment> payments) {
        List<Payment> successfulPayments = getSuccessfulPayments(payments);
        if (successfulPayments.size() == 0) {
            return Optional.empty();
        } else if (successfulPayments.size() == 1) {
            return Optional.of(payments.get(0));
        } else {
            throw new IllegalArgumentException(">1 successful payments in list");
        }
    }

    private List<Payment> getSuccessfulPayments(List<Payment> payments) {
        return payments.stream()
                .filter(payment -> payment.getResultCode() == ResultCodeEnum.AUTHORISED)
                .collect(Collectors.toList());
    }

    private boolean isAnySessionPaymentsSuccessful(List<Payment> payments) {
        return getSuccessfulPayments(payments).size() > 0;
    }

    public SessionResultResponse getSessionResult(
            String sessionId,
            String sessionResultToken) {
        try {
            return adyenPaymentsApi.getResultOfPaymentSession(sessionId, sessionResultToken);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

}
