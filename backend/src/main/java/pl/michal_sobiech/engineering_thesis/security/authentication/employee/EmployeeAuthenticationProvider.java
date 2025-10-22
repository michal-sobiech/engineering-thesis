package pl.michal_sobiech.engineering_thesis.security.authentication.employee;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.scope_username_password_authentication.EnterpriseIdAndUsername;
import pl.michal_sobiech.engineering_thesis.scope_username_password_authentication.EnterpriseIdUsernamePasswordAuthentication;
import pl.michal_sobiech.engineering_thesis.user.UserIdAuthentication;

@Component
@RequiredArgsConstructor
public class EmployeeAuthenticationProvider implements AuthenticationProvider {

    private static final Class<EnterpriseIdUsernamePasswordAuthentication> supportedInputAuthenticationClass = EnterpriseIdUsernamePasswordAuthentication.class;
    private final EmployeeService employeeService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserIdAuthentication authenticate(Authentication authentication) throws AuthenticationException {
        EnterpriseIdUsernamePasswordAuthentication castAuthentication = supportedInputAuthenticationClass
                .cast(authentication);
        EnterpriseIdAndUsername principal = castAuthentication.getPrincipal();
        long enterpriseId = principal.getEnterpriseId();
        String username = principal.getUsername();
        String password = castAuthentication.getCredentials();

        Optional<Employee> optionalEmployee = employeeService.findByEnterpriseIdAndUsername(enterpriseId, username);
        if (optionalEmployee.isEmpty()) {
            throw new BadCredentialsException("Employee doesn't exist");
        }
        Employee employee = optionalEmployee.get();

        String originalPasswordHash = employee.getPasswordHash();
        if (!passwordEncoder.matches(password, originalPasswordHash)) {
            throw new BadCredentialsException("Password hashes don't match");
        }

        return new UserIdAuthentication(employee.getUserId());
    }

    @Override
    public boolean supports(Class<?> inputAuthenticationClass) {
        return inputAuthenticationClass == supportedInputAuthenticationClass;
    }
}
