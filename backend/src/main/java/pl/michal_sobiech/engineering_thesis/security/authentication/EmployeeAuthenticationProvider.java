package pl.michal_sobiech.engineering_thesis.security.authentication;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.scope_username_password_authentication.EnterpriseIdAndUsername;
import pl.michal_sobiech.engineering_thesis.scope_username_password_authentication.EnterpriseIdUsernamePasswordAuthentication;
import pl.michal_sobiech.engineering_thesis.user.User;
import pl.michal_sobiech.engineering_thesis.user.UserDomain;
import pl.michal_sobiech.engineering_thesis.user.UserIdAuthentication;
import pl.michal_sobiech.engineering_thesis.user.UserService;

@Component
@RequiredArgsConstructor
public class EmployeeAuthenticationProvider implements AuthenticationProvider {

    private static final Class<EnterpriseIdUsernamePasswordAuthentication> supportedInputAuthenticationClass = EnterpriseIdUsernamePasswordAuthentication.class;

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserIdAuthentication authenticate(Authentication authentication) throws AuthenticationException {
        EnterpriseIdUsernamePasswordAuthentication castAuthentication = supportedInputAuthenticationClass
                .cast(authentication);
        EnterpriseIdAndUsername principal = castAuthentication.getPrincipal();
        long enterpriseId = principal.getEnterpriseId();
        String username = principal.getUsername();
        String password = castAuthentication.getCredentials();

        Optional<User> optionalUser = userService.findByEnterpriseIdAndUsername(enterpriseId, username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("Employee doesn't exist");
        }
        User user = optionalUser.get();

        String originalPasswordHash = user.getPasswordHash();
        if (!passwordEncoder.matches(password, originalPasswordHash)) {
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
