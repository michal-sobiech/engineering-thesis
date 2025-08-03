package pl.michal_sobiech.engineering_thesis.security.cors.cors_configuration_source;

import java.util.List;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.security.cors.CorsProperties;

@RequiredArgsConstructor
public class DefaultCorsConfigurationSource implements CorsConfigurationSource {

    private final CorsProperties properties;

    @Override
    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(properties.getAllowedOrigins());
        config.setAllowedMethods(List.of("GET", "POST"));
        return config;
    }
}
