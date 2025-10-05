package pl.michal_sobiech.engineering_thesis.jwt;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;

@Service
public class JwtCreationService {

    private final JwtSecretKey jwtSecretKey;
    private final Duration tokenDuration;

    public JwtCreationService(JwtSecretKey jwtSecretKey, JwtProperties properties) {
        this.jwtSecretKey = jwtSecretKey;
        this.tokenDuration = Duration.ofMinutes(properties.tokenDurationMinutes());
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
                .signWith(jwtSecretKey.getSecretKey(), jwtSecretKey.getJwtAlgorithm())
                .compact();
    }

}