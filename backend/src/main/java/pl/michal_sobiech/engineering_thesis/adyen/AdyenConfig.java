package pl.michal_sobiech.engineering_thesis.adyen;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.adyen.Client;
import com.adyen.enums.Environment;
import com.adyen.service.balanceplatform.AccountHoldersApi;
import com.adyen.service.balanceplatform.BalanceAccountsApi;
import com.adyen.service.checkout.PaymentsApi;
import com.adyen.service.legalentitymanagement.LegalEntitiesApi;

import lombok.RequiredArgsConstructor;

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

    @Bean
    public LegalEntitiesApi adyenLegalEntitiesApig(Client adyenClient) {
        return new LegalEntitiesApi(adyenClient);
    }

    @Bean
    public AccountHoldersApi adyenAccountHoldersApi(Client adyenClient) {
        return new AccountHoldersApi(adyenClient);
    }

    @Bean
    public BalanceAccountsApi adyenBalanceAccountsApi(Client adyenClient) {
        return new BalanceAccountsApi(adyenClient);
    }

}
