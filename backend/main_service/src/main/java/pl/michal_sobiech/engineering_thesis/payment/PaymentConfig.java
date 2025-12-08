package pl.michal_sobiech.engineering_thesis.payment;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pl.michal_sobiech.core.adyen.AdyenRefundService;
import pl.michal_sobiech.core.payment.RefundService;

@Configuration
public class PaymentConfig {

    @Bean
    public RefundService refundService(AdyenRefundService adyenRefundService) {
        return new RefundService(adyenRefundService);
    }

}
