package pl.michal_sobiech.engineering_thesis.adyen;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "adyen")
public record AdyenProperties(

        String merchantAccount,

        String apiKey

) {

}
