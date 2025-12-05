package pl.michal_sobiech.engineering_thesis.adyen;

import org.springframework.boot.context.properties.ConfigurationProperties;

import pl.michal_sobiech.core.adyen.AdyenProperties;

@ConfigurationProperties(prefix = "adyen")
public record SpringAdyenProperties(

        String merchantAccount,

        String apiKey,

        String hmacKey,

        String environment

) implements AdyenProperties {

}
