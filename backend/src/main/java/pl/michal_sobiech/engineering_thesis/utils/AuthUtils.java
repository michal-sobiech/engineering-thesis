package pl.michal_sobiech.engineering_thesis.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import pl.michal_sobiech.engineering_thesis.user.AuthPrincipal;
import pl.michal_sobiech.engineering_thesis.user.UserIdAuthentication;

public class AuthUtils {

    public static boolean isUserAlreadyAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() != null;
    }

    public static UserIdAuthentication getUserIdAuthentication() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof UserIdAuthentication userIdAuth) {
            return userIdAuth;
        } else {
            String message = String.format("Authentication is not an instance of %s",
                    UserIdAuthentication.class.getCanonicalName());
            throw new IllegalStateException(message);
        }
    }

    public static AuthPrincipal getAuthPrincipal() {
        final UserIdAuthentication userIdAuth = getUserIdAuthentication();
        return userIdAuth.getPrincipal();
    }

}
