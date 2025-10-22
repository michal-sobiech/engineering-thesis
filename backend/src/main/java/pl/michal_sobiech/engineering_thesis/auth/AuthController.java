package pl.michal_sobiech.engineering_thesis.auth;

import org.SwaggerCodeGenExample.api.AuthApi;
import org.SwaggerCodeGenExample.model.LogInEnterpriseEmployeeRequest;
import org.SwaggerCodeGenExample.model.LogInEnterpriseEmployeeResponse;
import org.SwaggerCodeGenExample.model.LogInIndependentEndUserRequest;
import org.SwaggerCodeGenExample.model.LogInIndependentEndUserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.jwt.JwtCreationService;
import pl.michal_sobiech.engineering_thesis.scope_username_password_authentication.EnterpriseIdUsernamePasswordAuthentication;
import pl.michal_sobiech.engineering_thesis.security.authentication.EmployeeAuthenticationProvider;
import pl.michal_sobiech.engineering_thesis.security.authentication.IndependentEndUserAuthenticationProvider;
import pl.michal_sobiech.engineering_thesis.security.authentication.StringUsernamePasswordAuthentication;
import pl.michal_sobiech.engineering_thesis.user.UserDomainAuthentication;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final EmployeeAuthenticationProvider employeeAuthenticationProvider;
    private final IndependentEndUserAuthenticationProvider independentEndUserAuthenticationProvider;
    private final JwtCreationService jwtCreationService;

    @Override
    public ResponseEntity<LogInEnterpriseEmployeeResponse> logInEnterpriseEmployee(
            LogInEnterpriseEmployeeRequest logInEnterpriseEmployeeRequest) {

        final var token = new EnterpriseIdUsernamePasswordAuthentication(
                logInEnterpriseEmployeeRequest.getEnterpriseId(),
                logInEnterpriseEmployeeRequest.getUsername(),
                logInEnterpriseEmployeeRequest.getPassword());

        UserDomainAuthentication authentication = employeeAuthenticationProvider.authenticate(token);
        String userIdAsString = String.valueOf(authentication.getPrincipal());
        String jwt = jwtCreationService.generateTokenNow(userIdAsString);

        var responseBody = new LogInEnterpriseEmployeeResponse(jwt);
        return ResponseEntity.ok(responseBody);
    }

    @Override
    public ResponseEntity<LogInIndependentEndUserResponse> logInIndependentEndUser(
            LogInIndependentEndUserRequest logInIndependentEndUserRequest) {
        final var token = new StringUsernamePasswordAuthentication(
                logInIndependentEndUserRequest.getEmail(),
                logInIndependentEndUserRequest.getPassword());

        UserDomainAuthentication authentication = independentEndUserAuthenticationProvider.authenticate(token);

        String userIdAsString = String.valueOf(authentication.getPrincipal());
        String jwt = jwtCreationService.generateTokenNow(userIdAsString);

        var responseBody = new LogInIndependentEndUserResponse(jwt);
        return ResponseEntity.ok(responseBody);
    }

}
