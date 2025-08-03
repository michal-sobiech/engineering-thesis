package pl.michal_sobiech.engineering_thesis.jwt;

import java.time.Instant;

public record GenerateJwtTokenRequest(

        String subject,
        String scope,
        Instant expiration

) {

}
