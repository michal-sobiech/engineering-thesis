package pl.michal_sobiech.engineering_thesis.jwt;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import pl.michal_sobiech.engineering_thesis.secret.Secret;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(

        Secret<String> secret,
        Duration tokenDuration,
        String signingAlgorithmType

) {
}
