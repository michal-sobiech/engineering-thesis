package pl.michal_sobiech.engineering_thesis.security.cors;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.RequiredArgsConstructor;

@ConfigurationProperties(prefix = "cors")
@RequiredArgsConstructor
public class CorsProperties {

    private final List<String> allowedOrigins;

    public List<String> getAllowedOrigins() {
        return allowedOrigins;
    }

}
