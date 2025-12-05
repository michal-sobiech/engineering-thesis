package pl.michal_sobiech.core.utils;

import java.util.Optional;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import pl.michal_sobiech.engineering_thesis.user.UserIdAuthentication;

public class AuthUtils {

    public static boolean isUserAlreadyAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (authentication != null && !(authentication instanceof AnonymousAuthenticationToken));
    }

    public static Optional<UserIdAuthentication> getUserIdAuthentication() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        if (auth instanceof UserIdAuthentication userIdAuth) {
            return Optional.of(userIdAuth);
        } else {
            return Optional.empty();
        }
    }

    public static Optional<Long> getAuthPrincipal() {
        final Optional<UserIdAuthentication> userIdAuth = getUserIdAuthentication();
        return userIdAuth.map(UserIdAuthentication::getPrincipal);
    }

}
