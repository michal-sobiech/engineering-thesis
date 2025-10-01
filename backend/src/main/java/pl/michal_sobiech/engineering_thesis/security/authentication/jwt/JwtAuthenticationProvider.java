package pl.michal_sobiech.engineering_thesis.security.authentication.jwt;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.user.UserIdAuthentication;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtDecoder jwtDecoder;

    private static final Class<JwtAuthentication> supportedInputAuthenticationClass = JwtAuthentication.class;

    @Override
    public UserIdAuthentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthentication castAuthentication = supportedInputAuthenticationClass.cast(authentication);

        String jwt = castAuthentication.getPrincipal();
        Jwt jwtClaims = jwtDecoder.decode(jwt);

        String subject = jwtClaims.getSubject();
        long userId = parseSubjectToIntUserId(subject);

        return new UserIdAuthentication(userId);
    }

    private int parseSubjectToIntUserId(String subject) {
        try {
            return Integer.parseInt(subject);
        } catch (Exception exception) {
            throw new IllegalStateException("Couldn't parse subject to user id int", exception);
        }
    }

    @Override
    public boolean supports(Class<?> inputAuthenticationClass) {
        return inputAuthenticationClass == supportedInputAuthenticationClass;
    }

}
