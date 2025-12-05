package pl.michal_sobiech.engineering_thesis.adyen;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.adyen.Client;
import com.adyen.enums.Environment;
import com.adyen.service.checkout.PaymentsApi;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.adyen.AdyenProperties;

@Configuration
@RequiredArgsConstructor
public class AdyenConfig {

    private final AdyenProperties adyenProperties;

    @Bean
    public Client adyenClient() {
        return new Client(adyenProperties.apiKey(), Environment.TEST);
    }

    @Bean
    public PaymentsApi adyenPaymentsApi(Client adyenClient) {
        return new PaymentsApi(adyenClient);
    }

}
