package pl.michal_sobiech.engineering_thesis.jwt;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
public class JwtConfig {

    @Bean
    public SecretKey secretKey(JwtProperties jwtProperties) {
        final String secret = jwtProperties.secret().getValue();
        final byte[] secretAsBytes = secret.getBytes(StandardCharsets.UTF_8);

        final String algoritm = jwtProperties.signingAlgorithmType();

        return new SecretKeySpec(secretAsBytes, algoritm);
    }

    @Bean
    public JwtDecoder jwtDecoder(SecretKey secretKey) {
        return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }

}
