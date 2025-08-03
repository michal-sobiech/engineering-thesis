package pl.michal_sobiech.engineering_thesis.jwt;

import java.time.Instant;

public record JwtToken(

                String scope,
                String subject,
                Instant issuedAt,
                Instant expiration

) {
}
