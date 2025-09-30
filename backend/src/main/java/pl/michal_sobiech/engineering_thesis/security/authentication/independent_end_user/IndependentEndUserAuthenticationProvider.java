package pl.michal_sobiech.engineering_thesis.security.authentication.independent_end_user;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.independent_end_user.IndependentEndUser;
import pl.michal_sobiech.engineering_thesis.independent_end_user.IndependentEndUserService;
import pl.michal_sobiech.engineering_thesis.security.authentication.StringUsernamePasswordAuthentication;
import pl.michal_sobiech.engineering_thesis.user.UserIdAuthentication;

@Service
@RequiredArgsConstructor
public class IndependentEndUserAuthenticationProvider implements AuthenticationProvider {

    private static final Class<StringUsernamePasswordAuthentication> supportedInputAuthenticationClass = StringUsernamePasswordAuthentication.class;

    private final IndependentEndUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final IndependentEndUserService independentEndUserService;

    @Override
    public UserIdAuthentication authenticate(Authentication authentication) throws AuthenticationException {
        StringUsernamePasswordAuthentication castAuthentication = supportedInputAuthenticationClass
                .cast(authentication);
        String username = castAuthentication.getPrincipal();
        String password = castAuthentication.getCredentials();
        User user = userDetailsService.loadUserByUsername(username);

        String originalPasswordHash = user.getPassword();
        if (!(passwordEncoder.matches(password, originalPasswordHash))) {
            throw new BadCredentialsException("Password hashes don't match");
        }

        // TODO remove double query (one is in loadByUsername)
        IndependentEndUser independentEndUser = independentEndUserService.findByUsername(username).orElseThrow();
        return new UserIdAuthentication(independentEndUser.getUserId());
    }

    @Override
    public boolean supports(Class<?> inputAuthenticationClass) {
        return inputAuthenticationClass == supportedInputAuthenticationClass;
    }

}
