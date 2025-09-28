package pl.michal_sobiech.engineering_thesis.jwt;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
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
        this.secretKey = createSecretKey(properties.secret());
        this.signingAlgorithm = createAlgorithm(properties.signingAlgorithmType());
        this.tokenDuration = properties.tokenDuration();
    }

    public String generateTokenNow(String subject) {
        Instant now = Instant.now();
        Instant expiration = now.plus(tokenDuration);

        GenerateJwtTokenRequest request = new GenerateJwtTokenRequest(
                subject,
                now,
                expiration);

        return generateToken(request);
    }

    public String generateToken(GenerateJwtTokenRequest request) {
        return Jwts.builder()
                .subject(request.subject())
                .issuedAt(Date.from(request.issuedAt()))
                .expiration(Date.from(request.expiresAt()))
                .signWith(secretKey, signingAlgorithm)
                .compact();
    }

    public JwtToken parseToken(String jwtToken) {
        JwtParser parser = Jwts.parser().decryptWith(secretKey).build();
        Jws<Claims> jws = parser.parseSignedClaims(jwtToken);
        Claims claims = jws.getPayload();

        String scope = claims.get("scope", String.class);
        String subject = claims.getSubject();
        Instant issuedAt = claims.getIssuedAt().toInstant();
        Instant expiration = claims.getExpiration().toInstant();
        return new JwtToken(scope, subject, issuedAt, expiration);
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