package pl.michal_sobiech.engineering_thesis.utils;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import pl.michal_sobiech.engineering_thesis.user.UserAuthentication;

public class AuthUtils {

    public static boolean isUserAlreadyAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() != null;
    }

    public static Optional<UserAuthentication> getUserIdAuthentication() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return Optional.empty();
        }

        if (auth instanceof UserAuthentication userIdAuth) {
            return Optional.of(userIdAuth);
        } else {
            String message = String.format("Authentication is not an instance of %s",
                    UserAuthentication.class.getCanonicalName());
            throw new IllegalStateException(message);
        }
    }

    public static Optional<Long> getAuthPrincipal() {
        final Optional<UserAuthentication> userIdAuth = getUserIdAuthentication();
        return userIdAuth.map(UserAuthentication::getPrincipal);
    }

}
