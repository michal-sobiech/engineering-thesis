package pl.michal_sobiech.engineering_thesis.security.session_management;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

public class SessionManagementSetupper {

    public HttpSecurity setupSessionManagement(HttpSecurity security) {
        var customizer = createCustomizer();
        return setCustomizer(security, customizer);
    }

    private Customizer<SessionManagementConfigurer<HttpSecurity>> createCustomizer() {
        return configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    private HttpSecurity setCustomizer(HttpSecurity security,
            Customizer<SessionManagementConfigurer<HttpSecurity>> customizer) {
        try {
            return security.sessionManagement(customizer);
        } catch (Exception exception) {
            String message = "Couldn't set session management";
            throw new IllegalStateException(message, exception);
        }
    }

}
