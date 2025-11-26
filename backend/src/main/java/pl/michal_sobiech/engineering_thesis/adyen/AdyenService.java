package pl.michal_sobiech.engineering_thesis.adyen;

import java.math.BigDecimal;
import java.net.URL;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.adyen.model.RequestOptions;
import com.adyen.model.checkout.Amount;
import com.adyen.model.checkout.CreateCheckoutSessionRequest;
import com.adyen.model.checkout.CreateCheckoutSessionResponse;
import com.adyen.service.checkout.PaymentsApi;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.appointment.AppointmentService;

@Service
@RequiredArgsConstructor
public class AdyenService {

    private final AdyenProperties adyenProperties;
    private final AppointmentService appointmentService;
    private final PaymentsApi adyenPaymentsApi;

    public CreateCheckoutSessionResponse createSession(long appointmentId, URL returnUrl) {
        var priceAndCurrency = appointmentService.getAppointmentPriceAndCurrency(appointmentId);

        Amount amount = new Amount();
        long priceMinorUnits = priceAndCurrency.getFirst().multiply(BigDecimal.valueOf(100)).longValue();
        amount.setCurrency(priceAndCurrency.getSecond().toString());
        amount.setValue(priceMinorUnits);

        CreateCheckoutSessionRequest request = new CreateCheckoutSessionRequest()
                .reference(UUID.randomUUID().toString())
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

}
