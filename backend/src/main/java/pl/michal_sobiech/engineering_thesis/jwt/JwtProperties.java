package pl.michal_sobiech.engineering_thesis.jwt;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import pl.michal_sobiech.engineering_thesis.secret.Secret;

@ConfigurationProperties(prefix = "jwt")
@Getter
public class JwtProperties {

    private final Secret<String> secret;
    private final Duration tokenDuration;
    private final String signingAlgorithmType;

}
