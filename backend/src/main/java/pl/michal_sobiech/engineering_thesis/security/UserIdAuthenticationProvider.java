package pl.michal_sobiech.engineering_thesis.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import pl.michal_sobiech.engineering_thesis.user.UserIdAuthentication;

@Service
public class UserIdAuthenticationProvider implements AuthenticationProvider {

    private static final Class<UserIdAuthentication> inputAuthenticationClass = UserIdAuthentication.class;

    @Override
    public UserIdAuthentication authenticate(Authentication authentication) {

    }

    @Override
    public boolean supports(Class<?> inputAuthenticationClass) {
        return inputAuthenticationClass == 
    }
}
