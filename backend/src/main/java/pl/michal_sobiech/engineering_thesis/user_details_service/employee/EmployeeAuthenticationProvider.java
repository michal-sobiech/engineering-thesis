package pl.michal_sobiech.engineering_thesis.user_details_service.employee;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.employee.Employee;
import pl.michal_sobiech.engineering_thesis.employee.EmployeeService;
import pl.michal_sobiech.engineering_thesis.scope_username_password_authentication.EnterpriseIdAndUsername;
import pl.michal_sobiech.engineering_thesis.scope_username_password_authentication.EnterpriseIdUsernamePasswordAuthentication;
import pl.michal_sobiech.engineering_thesis.user.UserIdAuthentication;
import pl.michal_sobiech.engineering_thesis.user.auth_principal.EmployeeAuthPrincipal;

@RequiredArgsConstructor
public class EmployeeAuthenticationProvider implements AuthenticationProvider {

    private static final Class<EnterpriseIdUsernamePasswordAuthentication> supportedInputAuthenticationClass = EnterpriseIdUsernamePasswordAuthentication.class;
    private final EmployeeService employeeService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserIdAuthentication authenticate(Authentication authentication) {
        EnterpriseIdUsernamePasswordAuthentication castAuthentication = supportedInputAuthenticationClass
                .cast(authentication);
        EnterpriseIdAndUsername principal = castAuthentication.getPrincipal();
        long enterpriseId = principal.getEnterpriseId();
        String username = principal.getUsername();
        String password = castAuthentication.getCredentials();

        Employee employee = employeeService.findByEnterpriseIdAndUsername(enterpriseId, username).orElseThrow();
        String originalPasswordHash = employee.getPasswordHash();
        if (!passwordEncoder.matches(password, originalPasswordHash)) {
            throw new RuntimeException("Password hashes don't match");
        }

        EmployeeAuthPrincipal authenticatedPrincipal = new EmployeeAuthPrincipal(
                employee.getUserId(),
                employee.getEmployeeId());
        return new UserIdAuthentication(authenticatedPrincipal);
    }

    @Override
    public boolean supports(Class<?> inputAuthenticationClass) {
        return inputAuthenticationClass == supportedInputAuthenticationClass;
    }
}
