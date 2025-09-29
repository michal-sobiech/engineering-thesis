package pl.michal_sobiech.engineering_thesis.utils;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import pl.michal_sobiech.engineering_thesis.user.UserIdAuthentication;
import pl.michal_sobiech.engineering_thesis.user.auth_principal.AuthPrincipal;

public class AuthUtils {

    public static boolean isUserAlreadyAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() != null;
    }

    public static Optional<UserIdAuthentication> getUserIdAuthenticationOrThrow() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return Optional.empty();
        }

        if (auth instanceof UserIdAuthentication userIdAuth) {
            return Optional.of(userIdAuth);
        } else {
            String message = String.format("Authentication is not an instance of %s",
                    UserIdAuthentication.class.getCanonicalName());
            throw new IllegalStateException(message);
        }
    }

    public static Optional<AuthPrincipal> getAuthPrincipal() {
        final Optional<UserIdAuthentication> userIdAuth = getUserIdAuthenticationOrThrow();
        return userIdAuth.map(UserIdAuthentication::getPrincipal);
    }

}
