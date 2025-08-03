package pl.michal_sobiech.engineering_thesis.utils;

import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtils {

    public static boolean isUserAlreadyAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() != null;
    }

}
