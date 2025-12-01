package pl.michal_sobiech.engineering_thesis.jwt;

import javax.crypto.SecretKey;

import io.jsonwebtoken.security.SecureDigestAlgorithm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JwtSecretKey {

    private final SecretKey secretKey;
    private final SecureDigestAlgorithm<SecretKey, ?> jwtAlgorithm;

}
