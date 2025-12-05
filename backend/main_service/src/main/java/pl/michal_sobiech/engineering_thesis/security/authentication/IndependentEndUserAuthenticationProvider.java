package pl.michal_sobiech.engineering_thesis.security.authentication;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.independent_end_user.IndependentEndUser;
import pl.michal_sobiech.core.independent_end_user.IndependentEndUserService;
import pl.michal_sobiech.engineering_thesis.user.UserIdAuthentication;

@Service
@RequiredArgsConstructor
public class IndependentEndUserAuthenticationProvider implements AuthenticationProvider {

    private static final Class<StringUsernamePasswordAuthentication> supportedInputAuthenticationClass = StringUsernamePasswordAuthentication.class;

    private final PasswordEncoder passwordEncoder;
    private final IndependentEndUserService independentEndUserService;

    @Override
    public UserIdAuthentication authenticate(Authentication authentication) throws AuthenticationException {
        StringUsernamePasswordAuthentication castAuthentication = supportedInputAuthenticationClass
                .cast(authentication);
        String username = castAuthentication.getPrincipal();
        String password = castAuthentication.getCredentials();

        Optional<IndependentEndUser> optionalIndependentEndUser = independentEndUserService.findByEmail(username);
        if (optionalIndependentEndUser.isEmpty()) {
            throw new UsernameNotFoundException("Indepentent end user doesn't exist");
        }
        IndependentEndUser independentEndUser = optionalIndependentEndUser.get();

        String originalPasswordHash = independentEndUser.getPasswordHash();
        if (!(passwordEncoder.matches(password, originalPasswordHash))) {
            throw new BadCredentialsException("Password hashes don't match");
        }

        return new UserIdAuthentication(independentEndUser.getUserId());
    }

    @Override
    public boolean supports(Class<?> inputAuthenticationClass) {
        return inputAuthenticationClass == supportedInputAuthenticationClass;
    }

}
