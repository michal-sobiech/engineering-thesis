package pl.michal_sobiech.engineering_thesis.jwt;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import pl.michal_sobiech.engineering_thesis.secret.Secret;

@Service
public class JwtService {

    private final SecretKey secretKey;
    private final SecureDigestAlgorithm<SecretKey, ?> signingAlgorithm;
    private final Duration tokenDuration;

    public JwtService(JwtProperties properties) {
        this.secretKey = createSecretKey(properties.getSecret());
        this.signingAlgorithm = createAlgorithm(properties.getSigningAlgorithmType());
        this.tokenDuration = properties.getTokenDuration();
    }

    public String generateToken(GenerateJwtTokenRequest request) {
        Instant now = Instant.now();
        Instant expiration = now.plus(tokenDuration);

        return Jwts.builder()
                .subject(request.username())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .signWith(secretKey, signingAlgorithm)
                .compact();
    }

    private SecretKey createSecretKey(Secret<String> secret) {
        byte[] secretAsBytes = secret.getValue().getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(secretAsBytes);
    }

    private SecureDigestAlgorithm<SecretKey, ?> createAlgorithm(String algorithmType) {
        return switch (algorithmType) {
            case "HS256" -> Jwts.SIG.HS256;
            default -> throw new IllegalArgumentException("Algoithm not supported: " + algorithmType);
        };
    }

}