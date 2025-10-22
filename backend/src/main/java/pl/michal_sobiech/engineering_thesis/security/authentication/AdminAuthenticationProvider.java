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
import pl.michal_sobiech.engineering_thesis.user.User;
import pl.michal_sobiech.engineering_thesis.user.UserDomain;
import pl.michal_sobiech.engineering_thesis.user.UserIdAuthentication;
import pl.michal_sobiech.engineering_thesis.user.UserService;

@Service
@RequiredArgsConstructor
public class AdminAuthenticationProvider implements AuthenticationProvider {

    private static final Class<StringUsernamePasswordAuthentication> supportedInputAuthenticationClass = StringUsernamePasswordAuthentication.class;

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Override
    public UserIdAuthentication authenticate(Authentication authentication) throws AuthenticationException {
        StringUsernamePasswordAuthentication castAuthentication = supportedInputAuthenticationClass
                .cast(authentication);
        String username = castAuthentication.getPrincipal();
        String password = castAuthentication.getCredentials();

        Optional<User> optionalUser = userService.findAdminByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("Admin doesn't exist");
        }
        User user = optionalUser.get();

        String originalPasswordHash = user.getPasswordHash();
        if (!(passwordEncoder.matches(password, originalPasswordHash))) {
            throw new BadCredentialsException("Password hashes don't match");
        }

        UserDomain userDomain = UserDomain.fromEntity(user);
        return new UserIdAuthentication(userDomain.getUserId());
    }

    @Override
    public boolean supports(Class<?> inputAuthenticationClass) {
        return inputAuthenticationClass == supportedInputAuthenticationClass;
    }

}
