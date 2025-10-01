package pl.michal_sobiech.engineering_thesis.security.no_auth_endpoints;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

@Component
public class NoAuthEndpointsSetupper {

    private final List<String> NO_AUTH_ENDPOINTS = new ArrayList<>(List.of());

    public HttpSecurity setupNoAuthEndpoints(HttpSecurity security) {
        var customizer = createCustomizer();
        return setCustomizer(security, customizer);
    }

    private Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> createCustomizer() {
        return registry -> registry.requestMatchers(NO_AUTH_ENDPOINTS.toArray(String[]::new)).permitAll().anyRequest()
                .authenticated();
    }

    private HttpSecurity setCustomizer(HttpSecurity security,
            Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> customizer) {
        try {
            return security.authorizeHttpRequests(customizer);
        } catch (Exception exception) {
            String message = "Couldn't add no auth endpoints";
            throw new IllegalStateException(message, exception);
        }
    }

}
