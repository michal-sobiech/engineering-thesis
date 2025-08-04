package pl.michal_sobiech.engineering_thesis.security.csrf;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;

public class CsrfSetupper {

    public HttpSecurity setupCsrf(HttpSecurity httpSecurity) {
        var customizer = createCustomizer();
        return setCustomizer(httpSecurity, customizer);
    }

    private Customizer<CsrfConfigurer<HttpSecurity>> createCustomizer() {
        return configurer -> configurer.disable();
    }

    private HttpSecurity setCustomizer(HttpSecurity security, Customizer<CsrfConfigurer<HttpSecurity>> customizer) {
        try {
            return security.csrf(customizer);
        } catch (Exception exception) {
            String message = "Couldn't set CSRF customizer";
            throw new IllegalStateException(message, exception);
        }
    }

}
