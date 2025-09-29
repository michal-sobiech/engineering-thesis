package pl.michal_sobiech.engineering_thesis.auth;

import org.SwaggerCodeGenExample.api.AuthApi;
import org.SwaggerCodeGenExample.model.LogInEnterpriseEmployeeRequest;
import org.SwaggerCodeGenExample.model.LogInEnterpriseEmployeeResponse;
import org.SwaggerCodeGenExample.model.LogInIndependentEndUserRequest;
import org.SwaggerCodeGenExample.model.LogInIndependentEndUserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.jwt.JwtCreationService;
import pl.michal_sobiech.engineering_thesis.scope_username_password_authentication.EnterpriseIdUsernamePasswordAuthentication;
import pl.michal_sobiech.engineering_thesis.user.UserIdAuthentication;
import pl.michal_sobiech.engineering_thesis.user_details_service.employee.UserIdAuthenticationProvider;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final UserIdAuthenticationProvider userIdAuthenticationProvider;
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final JwtCreationService jwtCreationService;

    @Override
    public ResponseEntity<LogInEnterpriseEmployeeResponse> logInEnterpriseEmployee(
            LogInEnterpriseEmployeeRequest logInEnterpriseEmployeeRequest) {

        final var token = new EnterpriseIdUsernamePasswordAuthentication(
                logInEnterpriseEmployeeRequest.getEnterpriseId(),
                logInEnterpriseEmployeeRequest.getUsername(),
                logInEnterpriseEmployeeRequest.getPassword());

        Authentication authentication = userIdAuthenticationProvider.authenticate(token);
        if (!(authentication instanceof UserIdAuthentication userIdAuthentication)) {
            String message = "Authentication instance is not an instance of UserIdAuthentication";
            throw new IllegalStateException(message);
        }

        String userIdAsString = String.valueOf(userIdAuthentication.getPrincipal().getUserId());
        String jwt = jwtCreationService.generateTokenNow(userIdAsString);

        var responseBody = new LogInEnterpriseEmployeeResponse(jwt);
        return ResponseEntity.ok(responseBody);
    }

    @Override
    public ResponseEntity<LogInIndependentEndUserResponse> logInIndependentEndUser(
            LogInIndependentEndUserRequest logInIndependentEndUserRequest) {
        final var token = new UsernamePasswordAuthenticationToken(
                logInIndependentEndUserRequest.getEmail(),
                logInIndependentEndUserRequest.getPassword());

        Authentication authentication = authenticationManager.authenticate(token);
        if (!(authentication instanceof UserIdAuthentication userIdAuthentication)) {
            String message = "Authentication instance is not an instance of UserIdAuthentication";
            throw new IllegalStateException(message);
        }

        String userIdAsString = String.valueOf(userIdAuthentication.getPrincipal().getUserId());
        String jwt = jwtCreationService.generateTokenNow(userIdAsString);

        var responseBody = new LogInIndependentEndUserResponse(jwt);
        return ResponseEntity.ok(responseBody);
    }

}
