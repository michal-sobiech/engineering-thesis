package pl.michal_sobiech.engineering_thesis.auth;

import org.SwaggerCodeGenExample.api.AuthApi;
import org.SwaggerCodeGenExample.model.LogInEnterpriseEmployeeRequest;
import org.SwaggerCodeGenExample.model.LogInEnterpriseEmployeeResponse;
import org.SwaggerCodeGenExample.model.LogInIndependentEndUserRequest;
import org.SwaggerCodeGenExample.model.LogInIndependentEndUserResponse;
import org.springframework.http.ResponseEntity;

public class AuthController implements AuthApi {

    ResponseEntity<LogInEnterpriseEmployeeResponse> logInEnterpriseEmployee(
            LogInEnterpriseEmployeeRequest logInEnterpriseEmployeeRequest) {

    }

    ResponseEntity<LogInIndependentEndUserResponse> logInIndependentEndUser(
            LogInIndependentEndUserRequest logInIndependentEndUserRequest) {

    }

}
