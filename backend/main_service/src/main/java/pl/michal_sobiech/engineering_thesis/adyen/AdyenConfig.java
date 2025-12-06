package pl.michal_sobiech.engineering_thesis.adyen;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.adyen.Client;
import com.adyen.enums.Environment;
import com.adyen.service.checkout.PaymentsApi;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.adyen.AdyenProperties;
import pl.michal_sobiech.core.adyen.AdyenSessionService;
import pl.michal_sobiech.core.appointment.AppointmentService;

@Configuration
@RequiredArgsConstructor
public class AdyenConfig {

    private final AdyenProperties adyenProperties;

    @Bean
    public Client adyenClient() {
        Client client = new Client(adyenProperties.apiKey(), Environment.TEST);
        System.out.println("|||||||||||||||||||||||||||||||||||||||");
        System.out.println(adyenProperties.apiKey());
        System.out.println(adyenProperties.merchantAccount());
        // System.out.println(client.getConfig().getApiKey());
        return client;
    }

    @Bean
    public PaymentsApi adyenPaymentsApi(Client adyenClient) {
        return new PaymentsApi(adyenClient);
    }

    @Bean
    public AdyenSessionService adyenSessionService(AdyenProperties adyenProperties, PaymentsApi adyenPaymentsApi,
            AppointmentService appointmentService) {
        return new AdyenSessionService(adyenProperties, adyenPaymentsApi, appointmentService);
    }

}
