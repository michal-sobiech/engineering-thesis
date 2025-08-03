package pl.michal_sobiech.engineering_thesis.security.cors;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CorsSetupper {

    private final CorsConfigurationSource corsConfigurationSource;

    public HttpSecurity setUpCors(HttpSecurity httpSecurity) {
        Customizer<CorsConfigurer<HttpSecurity>> customizer = configurer -> configurer
                .configurationSource(corsConfigurationSource);
        return setCustomizer(httpSecurity, customizer);
    }

    private HttpSecurity setCustomizer(HttpSecurity httpSecurity,
            Customizer<CorsConfigurer<HttpSecurity>> customizer) {
        try {
            return httpSecurity.cors(customizer);
        } catch (Exception exception) {
            String message = "Couldn't set CORS configuration.";
            throw new IllegalStateException(message, exception);
        }
    }
}
