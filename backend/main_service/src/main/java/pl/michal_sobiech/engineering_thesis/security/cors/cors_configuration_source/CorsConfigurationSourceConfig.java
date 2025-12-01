package pl.michal_sobiech.engineering_thesis.security.cors.cors_configuration_source;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfigurationSource;

import pl.michal_sobiech.engineering_thesis.security.cors.CorsProperties;

@Configuration
public class CorsConfigurationSourceConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource(CorsProperties properties) {
        return new DefaultCorsConfigurationSource(properties);
    }

}
