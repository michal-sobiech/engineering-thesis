package pl.michal_sobiech.engineering_thesis.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SecureDigestAlgorithm;

@Configuration
public class JwtConfig {

    private final static Map<String, String> jwtToJwc = Map.of(
            "HS256", "HmacSHA256");

    @Bean
    public JwtSecretKey jwtSecretKey(JwtProperties jwtProperties) {
        String secret = jwtProperties.secret().getValue();
        byte[] secretAsBytes = secret.getBytes(StandardCharsets.UTF_8);
        String jwtAlgorithmName = jwtProperties.signingAlgorithmType();
        String jwcAlgorithmName = jwtToJwc.get(jwtAlgorithmName);
        SecretKey secretKey = new SecretKeySpec(secretAsBytes, jwcAlgorithmName);

        SecureDigestAlgorithm<SecretKey, ?> jwtAlgorithm = createJwtAlgorithm(jwtAlgorithmName);

        return new JwtSecretKey(secretKey, jwtAlgorithm);
    }

    private SecureDigestAlgorithm<SecretKey, ?> createJwtAlgorithm(String algorithmType) {
        return switch (algorithmType) {
            case "HS256" -> Jwts.SIG.HS256;
            default -> throw new IllegalArgumentException("Algorithm not supported: " + algorithmType);
        };
    }

    @Bean
    public JwtDecoder jwtDecoder(JwtSecretKey jwtSecretKey) {
        SecretKey secretKey = jwtSecretKey.getSecretKey();
        return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }

}
